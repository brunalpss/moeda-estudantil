package com.moedaestudantil.controller;

import com.moedaestudantil.dto.LoginRequestDTO;
import com.moedaestudantil.dto.LoginResponseDTO;
import com.moedaestudantil.dto.StudentDTO;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody StudentDTO dto) {
        Student student = studentService.registerStudent(dto);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = studentService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(response);
    }

}

