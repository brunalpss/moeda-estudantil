package com.moedaestudantil.service.partner;

import com.moedaestudantil.dto.partner.PartnerCompanyDTO;
import com.moedaestudantil.dto.partner.PartnerCompanyLoginResponseDTO;
import com.moedaestudantil.entity.PartnerCompany;
import com.moedaestudantil.repository.PartnerCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerCompanyService {

    @Autowired
    private PartnerCompanyRepository repository;

    public PartnerCompany register(PartnerCompanyDTO dto) {
        PartnerCompany company = PartnerCompany.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .cnpj(dto.getCnpj())
                .password(dto.getPassword())
                .build();

        return repository.save(company);
    }

    public PartnerCompanyLoginResponseDTO login(String email, String password) {
        PartnerCompany company = repository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new PartnerCompanyLoginResponseDTO(
                company.getId(),
                company.getName(),
                company.getEmail()
        );
    }
}
