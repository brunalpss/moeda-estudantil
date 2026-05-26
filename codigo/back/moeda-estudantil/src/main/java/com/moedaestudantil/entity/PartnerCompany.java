package com.moedaestudantil.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
        /*
     * CODE REVIEW: O CNPJ é um identificador importante para a empresa parceira e deveria possuir
     * validação e restrição de unicidade. Recomenda-se aplicar validação no DTO de entrada e constraint
     * no banco de dados, como @Column(unique = true, nullable = false), evitando empresas duplicadas
     * ou registros sem identificação fiscal.
     */
    private String cnpj;
    private String email;
        /*
     * CODE REVIEW: A empresa parceira também possui um campo de senha diretamente na entidade,
     * repetindo o mesmo problema observado em Student e Teacher. Essa duplicação indica que a
     * autenticação poderia ser centralizada em uma estrutura comum, como uma entidade User ou um
     * AuthService responsável por cadastro, login e criptografia de senha para todos os perfis.
     */
    private String password;
}
