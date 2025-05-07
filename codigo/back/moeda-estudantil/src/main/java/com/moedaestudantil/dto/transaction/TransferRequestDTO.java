package com.moedaestudantil.dto.transaction;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long teacherId;
    private Long studentId;
    private int amount;
    private String message;
}
