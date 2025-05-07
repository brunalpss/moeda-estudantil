package com.moedaestudantil.service.transaction;

import com.moedaestudantil.dto.transaction.TransactionResponseDTO;
import com.moedaestudantil.dto.transaction.TransferRequestDTO;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.entity.Transaction;
import com.moedaestudantil.repository.StudentRepository;
import com.moedaestudantil.repository.TeacherRepository;
import com.moedaestudantil.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        System.out.println("[EMAIL SIMULADO] Enviado para: " + student.getEmail());
        System.out.println("Assunto: Você recebeu moedas!");
        System.out.println("Mensagem: Você recebeu " + dto.getAmount() + " moedas de " + teacher.getName() +
                ".\nMotivo: " + dto.getMessage());
        System.out.println("-----------------------------------");
    }

    public List<TransactionResponseDTO> getTransactionsByStudent(Long studentId) {
        return transactionRepository.findByRecipientId(studentId).stream()
                .map(tx -> new TransactionResponseDTO(
                        tx.getId(),
                        tx.getSender().getName(),
                        tx.getRecipient().getName(),
                        tx.getAmount(),
                        tx.getMessage(),
                        tx.getTimestamp()
                )).toList();
    }

    public List<TransactionResponseDTO> getTransactionsByTeacher(Long teacherId) {
        return transactionRepository.findBySenderId(teacherId).stream()
                .map(tx -> new TransactionResponseDTO(
                        tx.getId(),
                        tx.getSender().getName(),
                        tx.getRecipient().getName(),
                        tx.getAmount(),
                        tx.getMessage(),
                        tx.getTimestamp()
                )).toList();
    }

}
