package model

import DTO.Request.CandidatoRequestDTO
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
    List<String> competences

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

    Candidato(CandidatoRequestDTO request){
        this.first_name = request.first_name()
        this.last_name = request.last_name()
        this.email = request.email()
        this.description = request.description()
        this.CEP = request.CEP()
        this.city = request.city()
        this.CPF = request.CPF()
        this.password = request.password()
    }

    Candidato(long id, String first_name, String last_name, String email, String description, String CEP, String city, String CPF, List<String> competences) {
        this.id = id
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.description = description
        this.CEP = CEP
        this.city = city
        this.CPF = CPF
        this.competences = competences
    }

}

