package com.moedaestudantil.controller.partner;

import com.moedaestudantil.dto.partner.PartnerCompanyDTO;
import com.moedaestudantil.dto.partner.PartnerCompanyLoginResponseDTO;
import com.moedaestudantil.entity.PartnerCompany;
import com.moedaestudantil.service.partner.PartnerCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class PartnerCompanyController {

    @Autowired
    private PartnerCompanyService service;

    @PostMapping("/register")
    public ResponseEntity<PartnerCompany> register(@RequestBody PartnerCompanyDTO dto) {
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<PartnerCompanyLoginResponseDTO> login(@RequestBody PartnerCompanyDTO dto) {
        return ResponseEntity.ok(service.login(dto.getEmail(), dto.getPassword()));
    }
}
