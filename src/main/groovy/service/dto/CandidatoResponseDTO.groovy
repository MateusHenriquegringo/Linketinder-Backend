package service.dto

import com.fasterxml.jackson.annotation.JsonProperty
import enums.CompetenciaENUM

record CandidatoResponseDTO(
        long id,
        String first_name,
        String last_name,
        String cpf,
        String description,
        String email,
        String cep,
        String city,
        List<CompetenciaENUM> competences
) {

    long getId() {
        return id
    }

    @JsonProperty("first_name")
    String getFirst_name() {
        return first_name
    }

    @JsonProperty("last_name")
    String getLast_name() {
        return last_name
    }

    @JsonProperty("cpf")
    String getCPF() {
        return cpf
    }

    @JsonProperty("description")
    String getDescription() {
        return description
    }

    @JsonProperty("email")
    String getEmail() {
        return email
    }

    @JsonProperty("cep")
    String getCEP() {
        return cep
    }

    @JsonProperty("city")
    String getCity() {
        return city
    }

    @JsonProperty("competences")
    List<CompetenciaENUM> getCompetences() {
        return competences
    }
}
