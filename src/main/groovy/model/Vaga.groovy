package model

class Vaga {

    long id

    String vaga_name
    String description
    String city
    String state
    long empresaId

    List<String> competences

    Vaga(String vaga_name, String description, long empresaId, String state, String city) {
        this.vaga_name = vaga_name
        this.description = description
        this.empresaId = empresaId
        this.state = state
        this.city = city
    }
    Vaga(long id, String vaga_name, String description, String city, String state, long empresaId) {
        this.id = id
        this.vaga_name = vaga_name
        this.description = description
        this.empresaId = empresaId
        this.state = state
        this.city = city
    }

    Vaga(long id, String vaga_name, String description, String city, String state, long empresaId, List<String> competences) {
        this.id = id
        this.vaga_name = vaga_name
        this.description = description
        this.city = city
        this.state = state
        this.empresaId = empresaId
        this.competences = competences
    }
}
