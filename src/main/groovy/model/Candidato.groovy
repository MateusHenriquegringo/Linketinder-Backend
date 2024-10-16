package model


import enums.CompetenciaENUM

class Candidato {

    long id

    String first_name
    String last_name
    String cpf
    String description
    String email
    String cep
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
                ", CPF='" + cpf + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", CEP='" + cep + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", competences=" + competences +
                '}'
    }
}

