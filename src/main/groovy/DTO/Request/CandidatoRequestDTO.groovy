package DTO.Request

import enums.CompetenciasENUM

record CandidatoRequestDTO(
        String first_name,
        String last_name,
        String CPF,
        String description,
        String email,
        String CEP,
        String city,
        String password,
        List<CompetenciasENUM> competences
) {

}