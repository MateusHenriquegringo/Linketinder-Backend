package model


import enums.CompetenciaENUM

class Vaga {

    long id

    String vaga_name
    String description
    String city
    String state
    long empresaId

    List<CompetenciaENUM> competences

    Vaga() {}
}
