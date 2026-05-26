package com.moedaestudantil.service.student;

import com.moedaestudantil.dto.student.LoginResponseDTO;
import com.moedaestudantil.dto.student.StudentDTO;
import com.moedaestudantil.dto.student.StudentStatementDTO;
import com.moedaestudantil.dto.transaction.ReceivedTransactionDTO;
import com.moedaestudantil.dto.transaction.RedeemedRewardDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionResponseDTO;
import com.moedaestudantil.entity.EducationalInstitution;
import com.moedaestudantil.entity.Reward;
import com.moedaestudantil.entity.RewardRedemption;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.repository.*;
import com.moedaestudantil.service.email.EmailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class StudentService {
    /*
     * CODE REVIEW: A classe utiliza injeção por campo com @Autowired em vários atributos. Embora funcione,
     * essa abordagem dificulta testes unitários e deixa as dependências menos explícitas. Uma melhoria seria
     * usar injeção por construtor, preferencialmente com Lombok @RequiredArgsConstructor e atributos final,
     * facilitando mocks e aumentando a imutabilidade das dependências do serviço.
     */
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EducationalInstitutionRepository institutionRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardRedemptionRepository rewardRedemptionRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionRepository transactionRepository;
    /*
     * CODE REVIEW: O método redeemReward executa uma regra de negócio crítica, pois consulta aluno e recompensa,
     * valida saldo, cria o resgate, altera o saldo do aluno e envia e-mails. Esse fluxo deveria ser transacional
     * com @Transactional para evitar inconsistência caso alguma etapa falhe no meio da operação.
     */
    public RewardRedemptionResponseDTO redeemReward(Long studentId, Long rewardId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new EntityNotFoundException("Reward not found"));

        if (student.getBalance() < reward.getCost()) {
            throw new IllegalArgumentException("Insufficient balance to redeem reward");
        }

        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        RewardRedemption redemption = new RewardRedemption();
        redemption.setStudent(student);
        redemption.setReward(reward);
        redemption.setRedemptionCode(code);
        redemption.setRedeemedAt(LocalDateTime.now());
        /*
         * CODE REVIEW: O resgate é salvo antes da atualização do saldo do aluno. Sem uma transação envolvendo
         * as duas operações, pode ocorrer um cenário em que o resgate seja persistido, mas o saldo não seja
         * descontado corretamente. Recomenda-se garantir atomicidade entre criação do resgate e alteração do saldo.
         */
        rewardRedemptionRepository.save(redemption);

        student.setBalance(student.getBalance() - reward.getCost());
        studentRepository.save(student);
        /*
         * CODE REVIEW: O envio de e-mails está acoplado diretamente ao fluxo principal de resgate. Caso o serviço
         * de e-mail falhe, a operação de negócio pode ser impactada mesmo que o resgate e o desconto de saldo
         * estejam corretos. Uma alternativa seria publicar um evento de domínio ou executar o envio de e-mail
         * de forma assíncrona após a confirmação da transação.
         */
        emailService.sendRedemptionEmailToStudent(student.getEmail(), reward.getTitle(), code);
        emailService.sendNotificationToPartner(reward.getPartnerCompany().getEmail(), reward.getTitle(), student.getName(), code);

        return new RewardRedemptionResponseDTO(
                student.getName(),
                reward.getTitle(),
                code,
                reward.getPartnerCompany().getEmail(),
                student.getEmail()
        );
    }

    public StudentStatementDTO getStudentStatement(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<ReceivedTransactionDTO> receivedTransactions = getReceivedTransactionDTOS(student);

        List<RedeemedRewardDTO> redeemedRewards = getRedeemedRewardDTOS(student);

        return new StudentStatementDTO(
                student.getBalance(),
                receivedTransactions,
                redeemedRewards
        );
    }

    private List<RedeemedRewardDTO> getRedeemedRewardDTOS(Student student) {
        List<RedeemedRewardDTO> redeemedRewards = rewardRedemptionRepository
                .findByStudentId(student.getId())
                .orElse(Collections.emptyList())
                .stream()
                .map(rr -> new RedeemedRewardDTO(
                        rr.getRedeemedAt(),
                        rr.getReward().getTitle(),
                        rr.getRedemptionCode()
                ))
                .collect(Collectors.toList());
        return redeemedRewards;
    }

    private List<ReceivedTransactionDTO> getReceivedTransactionDTOS(Student student) {
        List<ReceivedTransactionDTO> receivedTransactions = transactionRepository
                .findByRecipientId(student.getId())
                .orElse(Collections.emptyList())
                .stream()
                .map(tx -> new ReceivedTransactionDTO(
                        tx.getTimestamp(),
                        tx.getAmount(),
                        tx.getSender().getName(),
                        tx.getMessage()
                ))
                .collect(Collectors.toList());
        return receivedTransactions;
    }


    public Student registerStudent(StudentDTO dto) {
        EducationalInstitution institution = institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .address(dto.getAddress())
                .course(dto.getCourse())
                /*
                 * CODE REVIEW: A senha recebida no DTO está sendo atribuída diretamente à entidade Student.
                 * Por segurança, a senha não deveria ser persistida em texto puro. Recomenda-se aplicar hash
                 * com BCryptPasswordEncoder antes de salvar o aluno no banco de dados.
                 */
                .password(dto.getPassword())
                .institution(institution)
                .build();

        return studentRepository.save(student);
    }

    /*
     * CODE REVIEW: O login depende de findByEmailAndPassword, o que pressupõe senha em texto puro no banco.
     * Uma arquitetura mais segura seria buscar o aluno apenas pelo e-mail e validar a senha com passwordEncoder.matches().
     * Isso também facilita a futura adoção de JWT ou outro mecanismo de autenticação.
     */
    public LoginResponseDTO login(String email, String password) {
        Student student = studentRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new LoginResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBalance()
        );
    }
}
