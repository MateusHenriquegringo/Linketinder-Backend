# Sistema de Curtidas e Matches

## Visão Geral

O sistema permite interações entre empresas e candidatos através de curtidas e matches, facilitando conexões e networking.

## Funcionalidades

- **Empresas**:
  - **Curtir Candidatos**: Curtir candidatos e registrar matches quando o candidato também curte a empresa.
  - **Verificar Matches**: Confirmar se há um match com um candidato.

- **Candidatos**:
  - **Curtir Empresas**: Curtir empresas e registrar matches quando a empresa também curte o candidato.
  - **Verificar Matches**: Confirmar se há um match com uma empresa.

## Classes

### `Empresa`

- **Propriedades**:
  - `nome`, `email`, `CNPJ`, `pais`, `estadoFederativo`, `CEP`, `descricao`
  - `curtidas` (lista de candidatos curtidos)
  - `matches` (lista de candidatos com os quais há um match)
  - `candidatos` (lista de candidatos da empresa)

- **Métodos**:
  - `getEndereco()`
  - `verificarMatch(Candidato candidato)`
  - `adicionarCandidato(Candidato candidato)`
  - `curtirCandidato(Candidato candidato)`

### `Candidato`

- **Propriedades**:
  - `nome`, `email`, `CPF`, `pais`, `estadoFederativo`, `CEP`, `descricao`
  - `curtidas` (lista de empresas curtidas)
  - `matches` (lista de empresas com as quais há um match)

- **Métodos**:
  - `getEndereco()`
  - `verificarMatch(Empresa empresa)`
  - `curtirEmpresa(Empresa empresa)`
