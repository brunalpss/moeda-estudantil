package com.moedaestudantil.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long teacherId;
    private Long studentId;
    private int amount;
    private String message;
}
