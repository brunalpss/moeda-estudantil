🔍 Sugestão de melhoria

Vi que em várias partes do repositório a camada de acesso a dados — como chamadas diretas a repositórios ou EntityManager — está misturada com a lógica de negócio ou de apresentação. Para melhorar a manutenção e testabilidade, recomendo extrair essa lógica para uma camada de DAO (Data Access Object) separada.

Benefícios da mudança

Baixo acoplamento: separa claramente a lógica de acesso ao banco e a lógica de negócio/aplicação.

Facilita testes unitários: você pode mockar os DAOs sem precisar de banco de dados real.

Escalabilidade e manutenibilidade: mudanças no acesso (como troca de tecnologia ou ajuste de consultas) ficam centralizadas no DAO.

📌 Sugestão de implementação

Crie uma interface, ex.: AlunoDAO, ProfessorDAO, TransacaoDAO, com métodos como findById(...), save(...), findByProfessor(...), findByAluno(...).

Implemente essas interfaces com JPA ou qualquer tecnologia escolhida.

Injete os DAOs nos serviços (AlunoService, ProfessorService) em vez do EntityManager diretamente.

Mova qualquer consulta ou persistência do serviço para o DAO correspondente.

------------------------------------------------
EXEMPLO ABAIXO
// Antes
public Aluno getAluno(Long id) {
    return entityManager.find(Aluno.class, id);
}

// Depois
public Aluno getAluno(Long id) {
    return alunoDAO.findById(id);
}
------------------------------------------------

Dessa forma, você mantém os serviços focados nas regras de negócio (e eventuais notificações por e-mail), enquanto os DAOs ficam responsáveis por toda a persistência.