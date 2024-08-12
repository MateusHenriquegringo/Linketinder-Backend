class Empresa implements Pessoa {

    String nome
    String email
    String CNPJ
    String pais
    String estadoFederativo
    String CEP
    String descricao

    List<Candidato> curtidas
    List<Candidato> matches

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
        if (verificarMatch(candidato)) {
            if (!matches.contains(candidato)) {
                matches << candidato
            }
        } else {
            if (!curtidas.contains(candidato)) {
                curtidas << candidato
            }
        }
    }
}
