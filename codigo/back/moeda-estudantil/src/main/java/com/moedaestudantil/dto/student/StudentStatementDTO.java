package com.moedaestudantil.dto.student;

import com.moedaestudantil.dto.transaction.ReceivedTransactionDTO;
import com.moedaestudantil.dto.transaction.RedeemedRewardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatementDTO {
    private Integer balance;
    private List<ReceivedTransactionDTO> receivedTransactions;
    private List<RedeemedRewardDTO> redeemedRewards;
}