package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Student;
import com.moedaestudantil.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findBySenderId(Long teacherId);
    List<Transaction> findByRecipientId(Long studentId);
}
