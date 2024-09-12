package model

class Empresa {

    long id

    String name
    String description
    String email
    String CNPJ
    String CEP
    String country
    String password

    Set<Candidato> likes
    Set<Candidato> matches

    Empresa(String name, String description, String email, String CNPJ, String CEP, String country, String password) {
        this.name = name
        this.description = description
        this.email = email
        this.CNPJ = CNPJ
        this.CEP = CEP
        this.country = country
        this.password = password
    }

    boolean verifyMatch(Candidato candidato) {
        if (candidato instanceof Candidato) {
            return candidato.getLikes().contains(this);
        } else
            throw new RuntimeException("Empresas so dao match com candidatos")
    }

    void likeCandidato (Candidato candidato) {
        verifyMatch(candidato) ? matches << candidato : likes << candidato
    }
}
