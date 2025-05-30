package com.moedaestudantil.dto.teacher;

import com.moedaestudantil.dto.transaction.SentTransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStatementDTO {
    private Integer balance;
    private List<SentTransactionDTO> sentTransactions;
}
