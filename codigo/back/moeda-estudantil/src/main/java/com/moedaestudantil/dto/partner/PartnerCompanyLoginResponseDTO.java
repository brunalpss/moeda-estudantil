package com.moedaestudantil.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartnerCompanyLoginResponseDTO {
    private Long id;
    private String name;
    private String email;
}
