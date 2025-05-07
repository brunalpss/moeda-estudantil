package com.moedaestudantil.dto.partner;

import lombok.Data;

@Data
public class RewardDTO {
    private String title;
    private String description;
    private int cost;
    private String imageUrl;
    private Long partnerCompanyId;
}
