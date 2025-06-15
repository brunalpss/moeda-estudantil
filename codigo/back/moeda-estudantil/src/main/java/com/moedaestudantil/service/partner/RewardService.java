package com.moedaestudantil.service.partner;

import com.moedaestudantil.dto.partner.RewardDTO;
import com.moedaestudantil.dto.partner.RewardResponseDTO;
import com.moedaestudantil.entity.PartnerCompany;
import com.moedaestudantil.entity.Reward;
import com.moedaestudantil.repository.PartnerCompanyRepository;
import com.moedaestudantil.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private PartnerCompanyRepository partnerCompanyRepository;

    public RewardResponseDTO createReward(RewardDTO dto) {
        PartnerCompany company = partnerCompanyRepository.findById(dto.getPartnerCompanyId())
                .orElseThrow(() -> new RuntimeException("Partner company not found"));

        Reward reward = Reward.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .cost(dto.getCost())
                .imageUrl(dto.getImageUrl())
                .partnerCompany(company)
                .build();

        reward = rewardRepository.save(reward);

        return new RewardResponseDTO(
                reward.getId(),
                reward.getTitle(),
                reward.getDescription(),
                reward.getCost(),
                reward.getImageUrl(),
                company.getName()
        );
    }

    public List<RewardResponseDTO> listByCompany(Long companyId) {
        return rewardRepository.findByPartnerCompanyId(companyId).stream()
                .map(r -> new RewardResponseDTO(
                        r.getId(),
                        r.getTitle(),
                        r.getDescription(),
                        r.getCost(),
                        r.getImageUrl(),
                        r.getPartnerCompany().getName()
                )).toList();
    }

    public List<RewardResponseDTO> getAllRewards() {
        List<Reward> rewards = rewardRepository.findAll();

        if (rewards.isEmpty()) {
            return new ArrayList<>();
        }

        return rewards.stream()
                .map(reward -> new RewardResponseDTO(
                        reward.getId(),
                        reward.getTitle(),
                        reward.getDescription(),
                        reward.getCost(),
                        reward.getImageUrl(),
                        reward.getPartnerCompany().getName()
                ))
                .collect(Collectors.toList());
    }
}
