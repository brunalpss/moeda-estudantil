INSERT INTO educational_institution (id, name, address, cnpj)
VALUES (1, 'PUC Minas', 'Av. Dom José Gaspar, 500', '12345678000190');

INSERT INTO student (id, name, email, password, cpf, rg, address, course, balance, institution_id)
VALUES (1, 'Alice Santos', 'alice@student.com', '123', '12345678901', 'MG1234567', 'Rua A, 123', 'Engenharia de Software', 200, 1);

INSERT INTO teacher (id, name, email, password, cpf, department, balance, institution_id)
VALUES (1, 'Carlos Oliveira', 'carlos@prof.com', '123', '98765432100', 'Informática', 1000, 1);

INSERT INTO partner_company (id, name, email, password, cnpj)
VALUES (1, 'Cantina da Uni', 'contato@cantina.com', '123', '99999999000100');

INSERT INTO reward (id, title, description, image_url, cost, partner_company_id)
VALUES (1, 'Desconto na cantina', 'Desconto de R$5 em refeições', 'data:image/png;base64,ABC123==', 100, 1);

INSERT INTO transaction (id, amount, message, timestamp, sender_id, recipient_id)
VALUES (1, 50, 'Participação em aula', CURRENT_TIMESTAMP, 1, 1);

INSERT INTO reward_redemption (id, redeemed_at, redemption_code, reward_id, student_id)
VALUES (1, CURRENT_TIMESTAMP, 'ABC12345', 1, 1);
