package com.moedaestudantil.controller.partner;

import com.moedaestudantil.dto.partner.RewardDTO;
import com.moedaestudantil.dto.partner.RewardResponseDTO;
import com.moedaestudantil.service.partner.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/register")
    public ResponseEntity<RewardResponseDTO> create(@RequestBody RewardDTO dto) {
        return ResponseEntity.ok(rewardService.createReward(dto));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<RewardResponseDTO>> listByCompany(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(rewardService.listByCompany(companyId));
    }

}
