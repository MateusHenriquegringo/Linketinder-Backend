package service.dto

import enums.CompetenciaENUM

class VagaResponseDTO {
    long id
    String vaga_name
    String description
    String city
    String state
    long empresaId
    List<CompetenciaENUM> competences

    VagaResponseDTO(long id, String vaga_name, String description, String city, String state, long empresaId, List<CompetenciaENUM> competences) {
        this.id = id
        this.vaga_name = vaga_name
        this.description = description
        this.city = city
        this.state = state
        this.empresaId = empresaId
        this.competences = competences
    }
}
