package model

import enums.CompetenciasENUM

class Vaga {

    long id

    String name
    String description
    String city
    String state
    long empresaId

    List<CompetenciasENUM> competences

    Vaga(String name, String description, List<CompetenciasENUM> competences, long empresaId, String state, String city) {
        this.name = name
        this.description = description
        this.competences = competences
        this.empresaId = empresaId
        this.state = state
        this.city = city
    }
}
