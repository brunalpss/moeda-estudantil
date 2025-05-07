# 🪙 Sistema de Moeda Estudantil

Sistema acadêmico que utiliza uma moeda virtual para reconhecer o mérito estudantil. Professores distribuem moedas a alunos, que podem trocá-las por vantagens oferecidas por empresas parceiras.

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Spring Data JPA
* Spring Security (login básico)
* H2 Database (em memória)
* Maven

---

## 🧱 Funcionalidades

### 👤 Alunos

* Cadastro e login
* Consulta de saldo e extrato
* Resgate de recompensas
* Histórico de resgates

### 👨‍🏫 Professores

* Cadastro e login
* Envio de moedas a alunos com mensagem
* Visualização de extrato (moedas enviadas)

### 🏢 Empresas Parceiras

* Cadastro e login
* Cadastro de vantagens (recompensas)
* Listagem de vantagens

---

## 📆 Estrutura de Pacotes

```
com.moedaestudantil
├── controller
│   ├── partner
│   ├── student
│   ├── teacher
│   └── transaction
├── dto
│   ├── auth
│   ├── partner
│   ├── student
│   ├── teacher
│   └── transaction
├── entity
├── repository
├── security
└── service
    ├── partner
    ├── student
    ├── teacher
    └── transaction
```

---

## 💻 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/moeda-estudantil.git
```

2. Navegue até a pasta do projeto:

```bash
cd moeda-estudantil
```

3. Compile e execute:

```bash
./mvnw spring-boot:run
```

4. Acesse o H2 Console:

```
http://localhost:8080/h2-console
```

* JDBC URL: `jdbc:h2:mem:moedaestudantil`
* User: `sa`
* Password: *(em branco)*

---

## 📅 Dados de exemplo (`data.sql`)

O projeto já vem com um `data.sql` que popula o banco com:

* Uma instituição de ensino
* Um aluno com 100 moedas
* Um professor com 1000 moedas
* Uma empresa parceira
* Uma recompensa cadastrada pela empresa

---

## 📨 Emails simulados

Sempre que um aluno:

* Recebe moedas de um professor, ou
* Resgata uma recompensa

Um "e-mail simulado" é impresso no console com os detalhes da operação.

---

## 📮 Endpoints principais

### Autenticação

| Método | Endpoint               | Descrição                 |
| ------ | ---------------------- | ------------------------- |
| POST   | `/api/students/login`  | Login de aluno            |
| POST   | `/api/teachers/login`  | Login de professor        |
| POST   | `/api/companies/login` | Login de empresa parceira |

### Aluno

| Método | Endpoint                                  | Descrição                   |
| ------ | ----------------------------------------- | --------------------------- |
| POST   | `/api/students/register`                  | Cadastro de aluno           |
| GET    | `/api/transactions/student/{studentId}`   | Extrato de moedas recebidas |
| POST   | `/api/rewards/redeem`                     | Resgatar recompensa         |
| GET    | `/api/rewards/redeem/student/{studentId}` | Histórico de resgates       |

### Professor

| Método | Endpoint                                | Descrição                  |
| ------ | --------------------------------------- | -------------------------- |
| POST   | `/api/teachers/register`                | Cadastro de professor      |
| POST   | `/api/transactions/transfer`            | Envio de moedas a aluno    |
| GET    | `/api/transactions/teacher/{teacherId}` | Extrato de moedas enviadas |

### Empresa Parceira

| Método | Endpoint                           | Descrição                     |
| ------ | ---------------------------------- | ----------------------------- |
| POST   | `/api/companies/register`          | Cadastro de empresa parceira  |
| POST   | `/api/rewards/register`            | Cadastrar recompensa          |
| GET    | `/api/rewards/company/{companyId}` | Listar recompensas da empresa |

---
