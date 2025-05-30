package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedTransactionDTO {
    private LocalDateTime date;
    private Integer amount;
    private String teacherName;
    private String message;
}
