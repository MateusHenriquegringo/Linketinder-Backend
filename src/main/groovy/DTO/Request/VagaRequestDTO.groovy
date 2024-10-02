package DTO.Request

import enums.CompetenciaENUM

record VagaRequestDTO(
        String vaga_name,
        String description,
        Long empresa_id,
        String state,
        String city,
        List<CompetenciaENUM> competences
) {

}