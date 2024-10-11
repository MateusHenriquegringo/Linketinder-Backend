package enums

enum LikeDirectionENUM {
    CANDIDATO_TO_EMPRESA,
    EMPRESA_TO_CANDIDATO

    @Override
    String toString() {
        return this.name()
    }
}