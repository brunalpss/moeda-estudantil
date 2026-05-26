package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    /*
     * CODE REVIEW: Assim como no repositório de aluno, o método findByEmailAndPassword mistura autenticação
     * com consulta direta ao banco e pressupõe senha em texto puro. Para melhorar a segurança, recomenda-se
     * buscar o professor apenas por e-mail, usando findByEmail(String email), e validar a senha no service
     * com um mecanismo de hash, como BCryptPasswordEncoder.matches().
     */
    Optional<Teacher> findByEmailAndPassword(String email, String password);

}
