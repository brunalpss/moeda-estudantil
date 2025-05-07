package com.moedaestudantil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherLoginResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int balance;
}
