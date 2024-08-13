class Empresa implements Pessoa {

    String nome
    String email
    String CNPJ
    String pais
    String estadoFederativo
    String CEP
    String descricao

    Set<Candidato> curtidas
    Set<Candidato> matches

    List<Candidato> candidatos

    @Override
    String getEndereco() {
        return "${this.CEP}, ${this.pais}, ${this.estadoFederativo}"
    }

    boolean verificarMatch(Candidato candidato) {
        return candidato.getCurtidas().contains(this)
    }

    void adicionarCandidato(Candidato candidato){
        candidatos << candidato
    }


    void curtirCandidato(Candidato candidato) {
        verificarMatch(candidato) ? matches << candidato : curtidas << candidato;
    }
}
