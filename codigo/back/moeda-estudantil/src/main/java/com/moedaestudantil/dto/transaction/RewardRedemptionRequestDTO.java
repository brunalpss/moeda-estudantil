package com.moedaestudantil.dto.transaction;

import lombok.Data;

@Data
public class RewardRedemptionRequestDTO {
    private Long studentId;
    private Long rewardId;
}
