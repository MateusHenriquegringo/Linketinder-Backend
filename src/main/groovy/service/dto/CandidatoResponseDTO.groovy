package service.dto

import enums.CompetenciaENUM

record CandidatoResponseDTO(
        long id,
        String first_name,
        String last_name,
        String CPF,
        String description,
        String email,
        String CEP,
        String city,
        List<CompetenciaENUM> competences
) {

    long getId() {
        return id
    }

    String getFirst_name() {
        return first_name
    }

    String getLast_name() {
        return last_name
    }

    String getCPF() {
        return CPF
    }

    String getDescription() {
        return description
    }

    String getEmail() {
        return email
    }

    String getCEP() {
        return CEP
    }

    String getCity() {
        return city
    }

    List<CompetenciaENUM> getCompetences() {
        return competences
    }
}
