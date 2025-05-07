package com.moedaestudantil.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    private String message;

    private LocalDateTime timestamp;

    @ManyToOne
    private Teacher sender;

    @ManyToOne
    private Student recipient;
}