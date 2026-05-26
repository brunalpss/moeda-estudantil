package com.moedaestudantil.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
/* CODE REVIEW: O uso de @Data em entidades JPA precisa de cuidado, pois ele gera automaticamente
 * getters, setters, equals, hashCode e toString. Em entidades com relacionamentos, isso pode gerar
 * problemas de recursão no toString ou comportamento inadequado no equals/hashCode. Uma alternativa
 * mais segura seria usar apenas @Getter e @Setter, definindo equals/hashCode de forma controlada.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 /* CODE REVIEW: Os campos principais da entidade não possuem restrições de validação ou constraints
     * de banco, como nullable = false ou unique = true. Para melhorar a integridade dos dados, seria
     * interessante aplicar constraints em campos como email e cpf, evitando cadastro duplicado ou registros
     * incompletos.
     */

    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private String course;
    private String password;
     /*
     * CODE REVIEW: Os campos principais da entidade não possuem restrições de validação ou constraints
     * de banco, como nullable = false ou unique = true. Para melhorar a integridade dos dados, seria
     * interessante aplicar constraints em campos como email e cpf, evitando cadastro duplicado ou registros
     * incompletos.
     */
    private int balance = 0;
    /*
     * CODE REVIEW: O relacionamento com EducationalInstitution não define fetch, optional ou join column.
     * Para deixar a modelagem mais explícita, seria interessante avaliar @ManyToOne(fetch = FetchType.LAZY)
     * e @JoinColumn(nullable = false), caso todo aluno obrigatoriamente pertença a uma instituição.
     */
    @ManyToOne
    private EducationalInstitution institution;
}
