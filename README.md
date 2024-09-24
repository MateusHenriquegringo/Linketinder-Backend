# Linketinder JDBC CRUD Application

Aplicação Java que utiliza JDBC para operações CRUD em **Candidatos**, **Empresas**, **Vagas** e **Competências** com PostgreSQL.

## Funcionalidades

- **CRUD Candidatos**: Gerenciar candidatos e suas competências.
- **CRUD Empresas**: Gerenciar empresas.
- **CRUD Vagas**: Gerenciar vagas e suas competências.
- **CRUD Competências**: Gerenciar competências.

## Tecnologias

- **Groovy**
- **JDBC**
- **PostgreSQL**
- **Gradle**
- **H2 Database**
- **Spock**


## Estrutura do Projeto

- **src/main/groovy**: Código-fonte.
    - **DAO**: Data Access Objects.
    - **DTO**: Data Transfer Objects.
    - **model**: Classes de modelo.
    - **Main**: Ponto de entrada.
    - **DB**: Configuracoes de Conexao.
- **src/test/groovy**: Testes.
- **sql/** : Definicao e populacao do banco de dados

## Exemplos

- **Adicionar um Candidato**:

    ```java
    Candidato candidato = new Candidato("123.456.789-00", "João", "Silva", "joao@email.com", "São Paulo", "01000-000", "Desenvolvedor Java", "senha123");
    candidatoDAO.createCandidato(candidato);
    ```

- **Listar Vagas**:

    ```java
    List<VagaDTO> vagas = vagaDAO.listAllVagas();
    vagas.forEach(System.out::println);
    ```

- **Atualizar Empresa**:

    ```java
    Empresa empresa = empresaDAO.findById(1);
    empresa.setName("Novo Nome da Empresa");
    empresaDAO.updateEmpresa(empresa);
    ```
