package com.moedaestudantil.repository;

import com.moedaestudantil.entity.PartnerCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerCompanyRepository extends JpaRepository<PartnerCompany, Long> {
    Optional<PartnerCompany> findByEmailAndPassword(String email, String password);

}
