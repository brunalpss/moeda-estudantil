package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByPartnerCompanyId(Long companyId);
}