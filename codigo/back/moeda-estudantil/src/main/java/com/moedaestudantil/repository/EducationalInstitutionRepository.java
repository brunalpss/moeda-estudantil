package com.moedaestudantil.repository;

import com.moedaestudantil.entity.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
}