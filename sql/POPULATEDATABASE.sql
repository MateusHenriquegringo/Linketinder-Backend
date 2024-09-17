INSERT INTO "Empresa" (name, description, email, cnpj, cep, country, password)
VALUES
('Tech Solutions', 'Consultoria em tecnologia com foco em desenvolvimento de software', 'contato@techsolutions.com', '12.345.678/0001-00', '01000-000', 'Brasil', 'senhaEmpresa1'),
('Innovative Labs', 'Startup de inovação focada em inteligência artificial e aprendizado de máquina', 'contato@innovativelabs.com', '98.765.432/0001-99', '20000-000', 'Brasil', 'senhaEmpresa2'),
('Global FinTech', 'Soluções financeiras utilizando blockchain e criptomoedas', 'contato@globalfintech.com', '22.333.444/0001-11', '30000-000', 'Estados Unidos', 'senhaEmpresa3'),
('Green Energy Solutions', 'Especializada em energias renováveis e sustentabilidade', 'contato@greenenergy.com', '55.666.777/0001-22', '40000-000', 'Brasil', 'senhaEmpresa4'),
('Digital Marketing Group', 'Agência de marketing digital focada em mídia social', 'contato@digitalmarketinggroup.com', '99.888.777/0001-33', '50000-000', 'Canadá', 'senhaEmpresa5');


INSERT INTO "Vaga" (name, description, empresa_id, state, city)
VALUES
('Desenvolvedor Full Stack', 'Desenvolvimento de aplicações web utilizando Java, Spring Boot e React.', 1, 'SP', 'São Paulo'),
('Engenheiro de Dados', 'Desenvolvimento de pipelines de dados e análise com Python e SQL.', 2, 'RJ', 'Rio de Janeiro'),
('Especialista em DevOps', 'Automatização de infraestrutura com Docker, Kubernetes e AWS.', 3, 'MG', 'Belo Horizonte'),
('Desenvolvedor Mobile', 'Desenvolvimento de aplicações móveis nativas utilizando Kotlin e Swift.', 4, 'PR', 'Curitiba'),
('Analista de Marketing Digital', 'Criação de campanhas de marketing digital em mídias sociais.', 5, 'SC', 'Florianópolis');

INSERT INTO "Vaga_Competencia" (vaga_id, competencia)
VALUES
(1, 1),  -- JAVA
(1, 2),  -- SPRING_BOOT
(1, 3),  -- REACT
(2, 4),  -- PYTHON
(2, 5),  -- SQL
(2, 6),  -- NOSQL
(3, 7),  -- DOCKER
(3, 8),  -- KUBERNETES
(3, 9),  -- AWS
(4, 10), -- KOTLIN
(4, 11), -- SWIFT
(4, 12), -- GIT

INSERT INTO "Candidato" (cpf, first_name, last_name, email, city, cep, description, password)
VALUES
('123.456.789-00', 'João', 'Silva', 'joao.silva@email.com', 'São Paulo', '01000-000', 'Desenvolvedor Full Stack com 5 anos de experiência', 'senha123'),
('987.654.321-00', 'Maria', 'Souza', 'maria.souza@email.com', 'Rio de Janeiro', '20000-000', 'Especialista em Frontend com foco em React e Angular', 'senha456'),
('111.222.333-44', 'Pedro', 'Oliveira', 'pedro.oliveira@email.com', 'Belo Horizonte', '30000-000', 'Engenheiro de Dados com experiência em Big Data e NoSQL', 'senha789'),
('555.666.777-88', 'Ana', 'Pereira', 'ana.pereira@email.com', 'Curitiba', '80000-000', 'DevOps com expertise em Docker, Kubernetes e AWS', 'senha101'),
('999.888.777-66', 'Luiz', 'Mendes', 'luiz.mendes@email.com', 'Florianópolis', '88000-000', 'Desenvolvedor Mobile especializado em Kotlin e Swift', 'senha202');

INSERT INTO "Candidato_Competencia" (candidato_id, competencia_id)
VALUES
(1, 1),  -- JAVA
(1, 2),  -- SPRING_BOOT
(1, 3),  -- REACT
(2, 3),  -- REACT
(2, 4),  -- JAVASCRIPT
(2, 5),  -- ANGULAR
(3, 4),  -- PYTHON
(3, 5),  -- SQL
(3, 6),  -- NOSQL
(4, 7),  -- DOCKER
(4, 8),  -- KUBERNETES
(4, 9),  -- AWS
(5, 10), -- KOTLIN
(5, 11), -- SWIFT
(5, 12); -- GIT