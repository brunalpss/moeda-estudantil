package com.moedaestudantil.service.teacher;

import com.moedaestudantil.dto.teacher.TeacherDTO;
import com.moedaestudantil.dto.teacher.TeacherLoginResponseDTO;
import com.moedaestudantil.dto.teacher.TeacherStatementDTO;
import com.moedaestudantil.dto.transaction.SentTransactionDTO;
import com.moedaestudantil.dto.transaction.TransferRequestDTO;
import com.moedaestudantil.entity.EducationalInstitution;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.entity.Transaction;
import com.moedaestudantil.repository.EducationalInstitutionRepository;
import com.moedaestudantil.repository.StudentRepository;
import com.moedaestudantil.repository.TeacherRepository;
import com.moedaestudantil.repository.TransactionRepository;
import com.moedaestudantil.service.email.EmailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EducationalInstitutionRepository institutionRepository;

    private final StudentRepository studentRepository;

    private final TransactionRepository transactionRepository;

    private final EmailService emailService;

    public Teacher register(TeacherDTO dto) {
        EducationalInstitution institution = institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Teacher teacher = Teacher.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .department(dto.getDepartment())
                .password(dto.getPassword())
                .institution(institution)
                .balance(1000)
                .build();

        return teacherRepository.save(teacher);
    }

    public TeacherLoginResponseDTO login(String email, String password) {
        Teacher teacher = teacherRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new TeacherLoginResponseDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getBalance()
        );
    }

    public TeacherStatementDTO getTeacherStatement(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        List<SentTransactionDTO> sentTransactions = transactionRepository
                .findBySenderId(teacher.getId())
                .orElse(Collections.emptyList())
                .stream()
                .map(tx -> new SentTransactionDTO(
                        tx.getTimestamp(),
                        tx.getRecipient().getName(),
                        tx.getAmount(),
                        tx.getMessage()
                ))
                .collect(Collectors.toList());

        return new TeacherStatementDTO(
                teacher.getBalance(),
                sentTransactions
        );
    }
}
