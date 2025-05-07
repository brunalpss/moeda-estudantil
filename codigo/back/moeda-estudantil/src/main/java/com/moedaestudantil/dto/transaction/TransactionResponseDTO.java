package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponseDTO {
    private Long transactionId;
    private String from;
    private String to;
    private int amount;
    private String message;
    private LocalDateTime timestamp;
}
