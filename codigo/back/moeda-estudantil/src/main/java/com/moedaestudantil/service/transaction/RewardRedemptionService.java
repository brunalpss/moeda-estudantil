package com.moedaestudantil.service.transaction;

import com.moedaestudantil.dto.transaction.RewardRedemptionHistoryDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionRequestDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionResponseDTO;
import com.moedaestudantil.entity.Reward;
import com.moedaestudantil.entity.RewardRedemption;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.repository.RewardRedemptionRepository;
import com.moedaestudantil.repository.RewardRepository;
import com.moedaestudantil.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RewardRedemptionService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RewardRedemptionRepository redemptionRepository;

    public RewardRedemptionResponseDTO redeem(RewardRedemptionRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Reward reward = rewardRepository.findById(dto.getRewardId())
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        if (student.getBalance() < reward.getCost()) {
            throw new RuntimeException("Student does not have enough coins");
        }

        student.setBalance(student.getBalance() - reward.getCost());
        studentRepository.save(student);

        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        RewardRedemption redemption = RewardRedemption.builder()
                .reward(reward)
                .student(student)
                .redemptionCode(code)
                .redeemedAt(LocalDateTime.now())
                .build();

        redemptionRepository.save(redemption);

        return new RewardRedemptionResponseDTO(
                student.getName(),
                reward.getTitle(),
                code,
                reward.getPartnerCompany().getEmail(),
                student.getEmail()
        );
    }

    public List<RewardRedemptionHistoryDTO> getRedemptionsByStudent(Long studentId) {
        return redemptionRepository.findByStudentId(studentId).stream()
                .map(r -> new RewardRedemptionHistoryDTO(
                        r.getReward().getTitle(),
                        r.getReward().getDescription(),
                        r.getReward().getCost(),
                        r.getRedemptionCode(),
                        r.getReward().getPartnerCompany().getName(),
                        r.getRedeemedAt()
                ))
                .toList();
    }
}
