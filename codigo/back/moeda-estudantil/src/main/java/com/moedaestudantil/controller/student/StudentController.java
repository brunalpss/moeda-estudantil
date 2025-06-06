package com.moedaestudantil.controller.student;

import com.moedaestudantil.dto.auth.LoginRequestDTO;
import com.moedaestudantil.dto.student.LoginResponseDTO;
import com.moedaestudantil.dto.student.StudentDTO;
import com.moedaestudantil.dto.student.StudentStatementDTO;
import com.moedaestudantil.dto.transaction.RewardRedemptionResponseDTO;
import com.moedaestudantil.entity.Student;
import com.moedaestudantil.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
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

    @PostMapping("/redeem/{studentId}/{rewardId}")
    public ResponseEntity<RewardRedemptionResponseDTO> redeemReward(
            @PathVariable Long studentId,
            @PathVariable Long rewardId) {

        RewardRedemptionResponseDTO response = studentService.redeemReward(studentId, rewardId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statement/{studentId}")
    public ResponseEntity<StudentStatementDTO> getStudentStatement(@PathVariable Long studentId) {
        StudentStatementDTO response = studentService.getStudentStatement(studentId);
        return ResponseEntity.ok(response);
    }

}

