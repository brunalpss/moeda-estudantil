package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmailAndPassword(String email, String password);

    Optional<Student> findByName(String name);

}