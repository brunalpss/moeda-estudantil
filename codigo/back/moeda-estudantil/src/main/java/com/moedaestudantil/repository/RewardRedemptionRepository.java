package com.moedaestudantil.repository;

import com.moedaestudantil.entity.RewardRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {
    Optional<List<RewardRedemption>> findByStudentId(Long studentId);

}