package com.moedaestudantil.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedeemedRewardDTO {
    private LocalDateTime redemptionDate;
    private String rewardTitle;
    private String code;
}