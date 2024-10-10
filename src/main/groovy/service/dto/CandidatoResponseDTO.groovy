package service.dto

import enums.CompetenciaENUM

class CandidatoResponseDTO {
    long id
    String first_name
    String last_name
    String CPF
    String description
    String email
    String CEP
    String city
    List<CompetenciaENUM> competences

    CandidatoResponseDTO(Long id, String first_name, String last_name, String CPF, String description, String email, String CEP, String city, List<CompetenciaENUM> competences) {
        this.id = id
        this.first_name = first_name
        this.last_name = last_name
        this.CPF = CPF
        this.description = description
        this.email = email
        this.CEP = CEP
        this.city = city
        this.competences = competences
    }
}
