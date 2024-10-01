package model

class Empresa {

    long id

    String empresa_name
    String description
    String email
    String CNPJ
    String CEP
    String country
    String password

    Set<Candidato> likes
    Set<Candidato> matches

    Empresa(String empresa_name, String description, String email, String CNPJ, String CEP, String country, String password) {
        this.empresa_name = empresa_name
        this.description = description
        this.email = email
        this.CNPJ = CNPJ
        this.CEP = CEP
        this.country = country
        this.password = password
    }

    Empresa(long id, String empresa_name, String description, String email, String CNPJ, String CEP, String country) {
        this.id = id
        this.empresa_name = empresa_name
        this.description = description
        this.email = email
        this.CNPJ = CNPJ
        this.CEP = CEP
        this.country = country
    }

}
