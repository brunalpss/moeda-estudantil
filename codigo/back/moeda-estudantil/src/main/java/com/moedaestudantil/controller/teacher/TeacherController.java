package com.moedaestudantil.controller.teacher;

import com.moedaestudantil.dto.teacher.TeacherDTO;
import com.moedaestudantil.dto.teacher.TeacherLoginResponseDTO;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<Teacher> register(@RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TeacherLoginResponseDTO> login(@RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.login(dto.getEmail(), dto.getPassword()));
    }
}
