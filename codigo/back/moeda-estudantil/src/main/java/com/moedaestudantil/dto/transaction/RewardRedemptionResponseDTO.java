package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardRedemptionResponseDTO {
    private String studentName;
    private String rewardTitle;
    private String redemptionCode;
    private String partnerEmail;
    private String studentEmail;
}
