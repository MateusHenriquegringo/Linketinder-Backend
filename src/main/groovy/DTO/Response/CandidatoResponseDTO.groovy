package DTO.Response

import groovy.transform.ImmutableOptions

@ImmutableOptions()
record CandidatoResponseDTO(
        long id,
        String first_name,
        String last_name,
        String CPF,
        String description,
        String email,
        String CEP,
        String city,
        List<String> competences
) {
}