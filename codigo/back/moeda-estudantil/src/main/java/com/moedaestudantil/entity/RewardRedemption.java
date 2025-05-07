package com.moedaestudantil.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardRedemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime redeemedAt;

    private String redemptionCode;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Reward reward;
}
