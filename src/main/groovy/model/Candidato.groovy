package model

import DTO.Request.CandidatoRequestDTO
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

    Set<Empresa> matches
    Set<Empresa> likes
    List<CompetenciaENUM> competences

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

    Candidato(CandidatoRequestDTO request){
        this.first_name = request.first_name()
        this.last_name = request.last_name()
        this.email = request.email()
        this.description = request.description()
        this.CEP = request.CEP()
        this.city = request.city()
        this.CPF = request.CPF()
        this.password = request.password()
        this.competences = request.competences()
    }

    Candidato(long id, String first_name, String last_name, String email, String description, String CEP, String city, String CPF, List<CompetenciaENUM> competences) {
        this.id = id
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.description = description
        this.CEP = CEP
        this.city = city
        this.CPF = CPF
        this.competences = competences as List<CompetenciaENUM>
    }

}

