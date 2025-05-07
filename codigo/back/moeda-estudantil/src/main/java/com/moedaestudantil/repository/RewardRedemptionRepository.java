package com.moedaestudantil.repository;

import com.moedaestudantil.entity.RewardRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {
    List<RewardRedemption> findByStudentId(Long studentId);

}