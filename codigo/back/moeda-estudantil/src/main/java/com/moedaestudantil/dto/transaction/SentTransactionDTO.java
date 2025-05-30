package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentTransactionDTO {
    private LocalDateTime date;
    private String studentName;
    private Integer amount;
    private String message;
}
