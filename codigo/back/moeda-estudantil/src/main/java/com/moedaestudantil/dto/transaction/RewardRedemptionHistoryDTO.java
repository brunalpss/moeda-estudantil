package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RewardRedemptionHistoryDTO {
    private String rewardTitle;
    private String description;
    private int cost;
    private String code;
    private String companyName;
    private LocalDateTime redeemedAt;
}
