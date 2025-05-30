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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

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

    public RewardRedemptionResponseDTO redeemReward(Long rewardId) {
        // TODO: Replace with authenticated student
        Student student = studentRepository.findById(1L)
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

        rewardRedemptionRepository.save(redemption);

        student.setBalance(student.getBalance() - reward.getCost());
        studentRepository.save(student);

        emailService.sendRedemptionEmailToStudent(student.getEmail(), reward.getTitle(), code);
        emailService.sendNotificationToPartner(reward.getPartnerCompany().getEmail(), reward.getTitle(), code);

        return new RewardRedemptionResponseDTO(
                student.getName(),
                reward.getTitle(),
                code,
                reward.getPartnerCompany().getEmail(),
                student.getEmail()
        );
    }

    public StudentStatementDTO getStudentStatement() {
        // TODO: Replace with authenticated student
        Student student = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<ReceivedTransactionDTO> receivedTransactions = transactionRepository
                .findByStudentId(student.getId()).stream()
                .map(tx -> new ReceivedTransactionDTO(
                        tx.getTimestamp(),
                        tx.getAmount(),
                        tx.getSender().getName(),
                        tx.getMessage()
                ))
                .collect(Collectors.toList());

        List<RedeemedRewardDTO> redeemedRewards = rewardRedemptionRepository
                .findByStudentId(student.getId()).stream()
                .map(rr -> new RedeemedRewardDTO(
                        rr.getRedeemedAt(),
                        rr.getReward().getTitle(),
                        rr.getRedemptionCode()
                ))
                .collect(Collectors.toList());

        return new StudentStatementDTO(
                student.getBalance(),
                receivedTransactions,
                redeemedRewards
        );
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
                .password(dto.getPassword())
                .institution(institution)
                .build();

        return studentRepository.save(student);
    }

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
