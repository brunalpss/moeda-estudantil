package com.moedaestudantil.service.student;

import com.moedaestudantil.dto.student.LoginResponseDTO;
import com.moedaestudantil.dto.student.StudentDTO;
import com.moedaestudantil.entity.EducationalInstitution;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.repository.EducationalInstitutionRepository;
import com.moedaestudantil.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EducationalInstitutionRepository institutionRepository;

    public Student registerStudent(StudentDTO dto) {
        EducationalInstitution institution = institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .address(dto.getAddress())
                .course(dto.getCourse())
                .password(dto.getPassword())
                .institution(institution)
                .build();

        return studentRepository.save(student);
    }

    public LoginResponseDTO login(String email, String password) {
        Student student = studentRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return new LoginResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBalance()
        );
    }
}
