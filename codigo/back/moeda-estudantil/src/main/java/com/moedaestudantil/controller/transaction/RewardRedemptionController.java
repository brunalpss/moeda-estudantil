package com.moedaestudantil.controller.transaction;

import com.moedaestudantil.dto.transaction.RewardRedemptionHistoryDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionRequestDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionResponseDTO;
import com.moedaestudantil.service.transaction.RewardRedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards/redeem")
public class RewardRedemptionController {

    @Autowired
    private RewardRedemptionService redemptionService;

    @PostMapping
    public ResponseEntity<RewardRedemptionResponseDTO> redeem(@RequestBody RewardRedemptionRequestDTO dto) {
        return ResponseEntity.ok(redemptionService.redeem(dto));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RewardRedemptionHistoryDTO>> getHistory(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(redemptionService.getRedemptionsByStudent(studentId));
    }

}
