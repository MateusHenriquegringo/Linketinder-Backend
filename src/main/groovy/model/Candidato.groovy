package model

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

    Candidato() {
    }

    Candidato(String CPF, String city, String CEP, String description, String email, String last_name, String first_name, List<Competencia> competencies, String password) {
        this.password = password
        this.CPF = CPF
        this.city = city
        this.CEP = CEP
        this.description = description
        this.email = email
        this.last_name = last_name
        this.first_name = first_name
        this.competencies = competencies
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


    Set<Empresa> matches
    Set<Empresa> likes
    List<Competencia> competencies

    void addCompetencie(Competencia skill) {
        competencies << skill
    }

    void listCompetencies() {
        println "Competências de ${first_name + ' '+ last_name}: ${competencies.join(', ')}"
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

