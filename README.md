# Linketinder JDBC CRUD Application

Aplicação Java que utiliza JDBC para operações CRUD em **Candidatos**, **Empresas**, **Vagas** e **Competências** com PostgreSQL.

## Funcionalidades

- **CRUD Candidatos**: Gerenciar candidatos e suas competências.
- **CRUD Empresas**: Gerenciar empresas.
- **CRUD Vagas**: Gerenciar vagas e suas competências.

## Tecnologias

- **Groovy**
- **JDBC**
- **PostgreSQL**
- **Gradle**
- **H2 Database**
- **Spock**
- **Flyway**
- **Tomcat**


## Estrutura do Projeto

- **src/main/groovy**: Código-fonte.
    - **dao**: Data Access Objects.
    - **controller**: Classes que tratam requisicoes HTTP.
    - **model**: Classes de modelo.
    - **repository**: Classes que gerenciam os DAOs e fornecem as funcionalidades abstraidas.
    - **service**: Classes que mapeiam os DTO de resposta e a logica de negocio.
    - **DB**: Configuracoes de Conexao.
- **src/test/groovy**: Testes.
- **webapp/** : Configuracao de servlets Tomcat

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
## Modelagem do banco de dados;
```mermaid
erDiagram
    CANDIDATO {
        int id
        string nome
        string email
        string cidade
        string telefone
        string status
    }
    
    VAGA_COMPETENCIA {
        int vaga_id FK
        enum competences    
    }
    
    CANDIDATO_COMPETENCIA {
        int candidato_id FK
        enum competences  
    }
    EMPRESA {
        int id PK
        string empressa_name  
        string description  
        string email
        string cnpj
        string cep
        string country
        string password
    }
    VAGA {
        int id PK
        int empresa_id FK
        string vaga_name
        string description
        string state
        string city
    }
    
    CANDIDATO_LIKES {
        int candidato_id FK
        int vaga_id FK
    }
    
    EMPRESA_LIKES {
        int empresa_id FK
        int candidato_id FK
    }
    
    MATCH {
        int candidato_id FK
        int empresa_id FK
    }

    %% Relacionamentos com Cardinalidades
    CANDIDATO ||--o| CANDIDATO_COMPETENCIA : "tem"
    VAGA ||--o| VAGA_COMPETENCIA : "requer"
    CANDIDATO ||--o| CANDIDATO_LIKES : "curte"
    VAGA ||--o| CANDIDATO_LIKES : "é curtida por"
    EMPRESA ||--o| EMPRESA_LIKES : "curte"
    CANDIDATO ||--o| EMPRESA_LIKES : "é curtido por"
    CANDIDATO ||--o| MATCH : "faz match"
    EMPRESA ||--o| MATCH : "faz match"
    EMPRESA ||--o| VAGA : "publica"