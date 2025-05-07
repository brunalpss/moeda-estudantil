package com.moedaestudantil.controller.transaction;

import com.moedaestudantil.dto.transaction.TransactionResponseDTO;
import com.moedaestudantil.dto.transaction.TransferRequestDTO;
import com.moedaestudantil.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO dto) {
        transactionService.transferCoins(dto);
        return ResponseEntity.ok("Transfer completed successfully");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TransactionResponseDTO>> getStudentTransactions(@PathVariable Long studentId) {
        return ResponseEntity.ok(transactionService.getTransactionsByStudent(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTeacherTransactions(@PathVariable Long teacherId) {
        return ResponseEntity.ok(transactionService.getTransactionsByTeacher(teacherId));
    }
}
