package com.moedaestudantil.dto;

import lombok.Data;

@Data
public class TeacherDTO {
    private String name;
    private String email;
    private String cpf;
    private String department;
    private String password;
    private Long institutionId;
}
