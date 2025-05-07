package com.moedaestudantil.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardResponseDTO {
    private Long id;
    private String title;
    private String description;
    private int cost;
    private String imageUrl;
    private String companyName;
}
