package com.moedaestudantil.controller.institution;

import com.moedaestudantil.dto.institution.InstitutionResponseDTO;
import com.moedaestudantil.service.institution.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
public class EducationInstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping()
    public ResponseEntity<List<InstitutionResponseDTO>> getAllInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }

}
