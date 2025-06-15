package com.moedaestudantil.service.institution;

import com.moedaestudantil.dto.institution.InstitutionResponseDTO;
import com.moedaestudantil.repository.EducationalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    @Autowired
    private EducationalInstitutionRepository institutionRepository;

    public List<InstitutionResponseDTO> getAllInstitutions() {
        return institutionRepository.getAllInstitutions();
    }
}
