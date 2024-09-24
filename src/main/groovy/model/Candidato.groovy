package model

import enums.CompetenciasENUM

class Candidato {

    long id

    String first_name
    String last_name
    String CPF
    String description
    String email
    String CEP
    String city
    String password

    Set<Empresa> matches
    Set<Empresa> likes
    List<CompetenciasENUM> competences


    Candidato(String CPF, String city, String CEP, String description, String email, String last_name, String first_name, List<CompetenciasENUM> competencies, String password) {
        this.password = password
        this.CPF = CPF
        this.city = city
        this.CEP = CEP
        this.description = description
        this.email = email
        this.last_name = last_name
        this.first_name = first_name
        this.competences = competencies
    }

    Candidato(long id, String first_name, String last_name, String email, String description, String CEP, String city, String CPF) {
        this.id = id
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.description = description
        this.CEP = CEP
        this.city = city
        this.CPF = CPF
    }

    Candidato(String first_name, String last_name, String email, String description, String CEP, String city, String CPF, String password) {
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.description = description
        this.CEP = CEP
        this.city = city
        this.CPF = CPF
        this.password = password
    }

    void addCompetencie(CompetenciasENUM skill) {
        competences << skill
    }

    void listCompetencies() {
        println "Competências de ${first_name + ' '+ last_name}: ${competences.join(', ')}"
    }


    boolean verifyMatch(Empresa empresa) {
        if (empresa instanceof Empresa) {
            return empresa.getLikes().contains(this);
        } else
            throw new RuntimeException("Candidatos so dao match com empresas")    }

    void likeEmpresa(Empresa empresa) {
        verifyMatch(empresa) ? matches << empresa : likes << empresa;
    }

}

