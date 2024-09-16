package model

class Competencia {

    long id
    String name

    Competencia(String name) {
        this.name = name
    }
    Competencia(long id, String name) {
        this.id = id
        this.name = name
    }
}

