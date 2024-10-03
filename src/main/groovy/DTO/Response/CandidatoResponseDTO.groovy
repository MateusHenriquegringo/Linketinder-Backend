package DTO.Response

import enums.CompetenciaENUM
import groovy.transform.ImmutableOptions

@ImmutableOptions()
record CandidatoResponseDTO(
        Long id,
        String first_name,
        String last_name,
        String CPF,
        String description,
        String email,
        String CEP,
        String city
) {
}