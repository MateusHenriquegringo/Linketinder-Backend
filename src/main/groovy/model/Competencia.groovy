package model

class Competencia {

    long id
    String description

    Competencia(String description) {
        this.description = description
    }
    Competencia(long id, String description) {
        this.id = id
        this.description = description
    }
}

