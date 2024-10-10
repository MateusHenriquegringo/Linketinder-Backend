package service.dto

import enums.CompetenciaENUM

record VagaResponseDTO(
        long id,
        String vaga_name,
        String description,
        String city,
        String state,
        long empresaId,
        List<CompetenciaENUM> competences
) {
    long getId() {
        return id
    }

    String getVaga_name() {
        return vaga_name
    }

    String getDescription() {
        return description
    }

    String getCity() {
        return city
    }

    String getState() {
        return state
    }

    long getEmpresaId() {
        return empresaId
    }

    List<CompetenciaENUM> getCompetences() {
        return competences
    }
}
