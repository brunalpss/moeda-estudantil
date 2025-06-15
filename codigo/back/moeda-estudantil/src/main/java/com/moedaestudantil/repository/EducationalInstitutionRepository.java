package com.moedaestudantil.repository;

import com.moedaestudantil.dto.institution.InstitutionResponseDTO;
import com.moedaestudantil.entity.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {

    @Query("SELECT new com.moedaestudantil.dto.institution.InstitutionResponseDTO(CAST(e.id as string), e.name) FROM EducationalInstitution e")
    List<InstitutionResponseDTO> getAllInstitutions();


}