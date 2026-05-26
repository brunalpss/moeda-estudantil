package com.moedaestudantil.repository;

import com.moedaestudantil.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
     /*
     * CODE REVIEW: O método findByEmailAndPassword acopla a autenticação diretamente à consulta no banco.
     * Essa abordagem dificulta o uso de senhas criptografadas, pois a senha não deveria ser pesquisada em
     * texto puro. Uma alternativa mais segura seria buscar apenas por e-mail, com findByEmail(String email),
     * e validar a senha no service usando BCryptPasswordEncoder.matches().
     */
    Optional<Student> findByEmailAndPassword(String email, String password);
  /*
     * CODE REVIEW: A busca de aluno por nome pode gerar ambiguidade, pois nomes não são identificadores únicos.
     * Em regras críticas, como transferência de moedas, seria mais seguro localizar o aluno por id, e-mail ou
     * matrícula. Isso reduz o risco de transferir moedas para o aluno errado em caso de homônimos.
     */
    Optional<Student> findByName(String name);

}
