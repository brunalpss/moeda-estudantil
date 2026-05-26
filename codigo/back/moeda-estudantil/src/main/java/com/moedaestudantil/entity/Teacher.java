package com.moedaestudantil.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String cpf;
    private String department;
     /*
     * CODE REVIEW: A senha do professor está modelada diretamente como String na entidade, assim como ocorre
     * em Student. Para evitar armazenamento de senha em texto puro, recomenda-se persistir apenas o hash da senha,
     * usando BCrypt ou mecanismo equivalente. Essa regra deveria ser centralizada em um serviço de autenticação,
     * evitando que cada tipo de usuário trate credenciais separadamente.
     */
    private String password;
/*
     * CODE REVIEW: O saldo do professor representa uma regra importante do domínio, pois dele saem as moedas
     * transferidas aos alunos. Como o uso de @Data gera setter público para balance, qualquer camada poderia alterar
     * esse valor diretamente. Uma melhoria seria encapsular o saldo com métodos de crédito/débito e validações de
     * negócio, impedindo saldo negativo ou alterações indevidas.
     */
    private int balance = 0;
 /*
     * CODE REVIEW: O relacionamento com EducationalInstitution não explicita se a instituição é obrigatória.
     * Caso todo professor deva pertencer a uma instituição, recomenda-se usar @JoinColumn(nullable = false)
     * e avaliar FetchType.LAZY para evitar carregamento desnecessário de dados relacionados.
     */
    @ManyToOne
    private EducationalInstitution institution;
}
