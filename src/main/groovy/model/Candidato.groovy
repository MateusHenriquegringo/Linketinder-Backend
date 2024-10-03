package model


import enums.CompetenciaENUM

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

    List<CompetenciaENUM> competences

    Candidato() {}

    @Override
    String toString() {
        return "Candidato{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", CPF='" + CPF + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", CEP='" + CEP + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", competences=" + competences +
                '}'
    }
}

