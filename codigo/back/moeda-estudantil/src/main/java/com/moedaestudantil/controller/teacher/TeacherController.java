package com.moedaestudantil.controller.teacher;

import com.moedaestudantil.dto.teacher.TeacherDTO;
import com.moedaestudantil.dto.teacher.TeacherLoginResponseDTO;
import com.moedaestudantil.dto.teacher.TeacherStatementDTO;
import com.moedaestudantil.dto.transaction.TransferRequestDTO;
import com.moedaestudantil.entity.Teacher;
import com.moedaestudantil.service.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
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

    @PostMapping("/transfer")
    public ResponseEntity<?> transferCoins(@RequestBody TransferRequestDTO request) {
        teacherService.transferCoins(request);
        return ResponseEntity.ok(Map.of("message", "Coins successfully transferred"));
    }

    @GetMapping("/statement")
    public ResponseEntity<TeacherStatementDTO> getTeacherStatement() {
        TeacherStatementDTO response = teacherService.getTeacherStatement();
        return ResponseEntity.ok(response);
    }
}
