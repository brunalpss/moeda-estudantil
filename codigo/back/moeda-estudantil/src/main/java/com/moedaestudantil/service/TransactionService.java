package com.moedaestudantil.service;

import com.moedaestudantil.dto.TransferRequestDTO;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.entity.Transaction;
import com.moedaestudantil.repository.StudentRepository;
import com.moedaestudantil.repository.TeacherRepository;
import com.moedaestudantil.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void transferCoins(TransferRequestDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (dto.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        if (teacher.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Teacher does not have enough coins");
        }

        teacher.setBalance(teacher.getBalance() - dto.getAmount());
        student.setBalance(student.getBalance() + dto.getAmount());

        teacherRepository.save(teacher);
        studentRepository.save(student);

        Transaction tx = Transaction.builder()
                .sender(teacher)
                .recipient(student)
                .amount(dto.getAmount())
                .message(dto.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(tx);

        // TODO Aqui será simulado o envio de email futuramente
    }
}
