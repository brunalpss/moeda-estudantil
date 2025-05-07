package com.moedaestudantil.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private String course;
    private String password;
    private Long institutionId;
}