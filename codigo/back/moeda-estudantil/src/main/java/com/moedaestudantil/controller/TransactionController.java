package com.moedaestudantil.controller;

import com.moedaestudantil.dto.TransferRequestDTO;
import com.moedaestudantil.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
