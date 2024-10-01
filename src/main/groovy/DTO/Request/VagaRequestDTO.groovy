package DTO.Request

import enums.CompetenciasENUM

record VagaRequestDTO(
        String vaga_name,
        String description,
        Long empresa_id,
        String state,
        String city,
        List<CompetenciasENUM> competences
) {

}