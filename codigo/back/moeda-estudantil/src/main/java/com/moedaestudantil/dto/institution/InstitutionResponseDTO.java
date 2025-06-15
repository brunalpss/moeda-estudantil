package com.moedaestudantil.dto.institution;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitutionResponseDTO {
    private String id;
    private String name;

    public InstitutionResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
