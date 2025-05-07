INSERT INTO educational_institution (id, name, cnpj, address)
VALUES (1, 'Universidade Teste', '12345678000199', 'Av. Principal, 123');

INSERT INTO student (id, name, email, cpf, rg, address, course, password, balance, institution_id)
VALUES (2, 'Aluno Resgatador', 'aluno2@exemplo.com', '98765432100', 'RJ654321', 'Rua C, 300', 'Administração', 'senha', 100, 1);

INSERT INTO teacher (id, name, email, cpf, department, password, balance, institution_id)
VALUES (1, 'Prof. João', 'joao@exemplo.com', '12345678901', 'Matemática', 'senha123', 1000, 1);

INSERT INTO partner_company (id, name, email, cnpj, password)
VALUES (1, 'Loja Parceira', 'contato@lojaparceira.com', '11222333000100', 'senha123');

INSERT INTO reward (id, title, description, cost, image_url, partner_company_id)
VALUES (1, 'Desconto na cantina', '10% em qualquer lanche', 50, 'https://example.com/lanche.jpg', 1);
