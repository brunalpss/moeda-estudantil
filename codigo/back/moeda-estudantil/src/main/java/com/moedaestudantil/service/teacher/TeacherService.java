package com.moedaestudantil.service.teacher;

import com.moedaestudantil.dto.teacher.TeacherDTO;
import com.moedaestudantil.dto.teacher.TeacherLoginResponseDTO;
import com.moedaestudantil.entity.EducationalInstitution;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.repository.EducationalInstitutionRepository;
import com.moedaestudantil.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EducationalInstitutionRepository institutionRepository;

    public Teacher register(TeacherDTO dto) {
        EducationalInstitution institution = institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Teacher teacher = Teacher.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .department(dto.getDepartment())
                .password(dto.getPassword())
                .institution(institution)
                .build();

        return teacherRepository.save(teacher);
    }

    public TeacherLoginResponseDTO login(String email, String password) {
        Teacher teacher = teacherRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new TeacherLoginResponseDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getBalance()
        );
    }
}
