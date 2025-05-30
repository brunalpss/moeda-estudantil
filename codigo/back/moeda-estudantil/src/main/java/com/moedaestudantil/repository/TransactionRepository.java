package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Student;
import com.moedaestudantil.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderId(Long teacherId);
    List<Transaction> findByRecipientId(Long studentId);

    List<Transaction> findByStudentId(Long studentId);
}
