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

    @Override
    boolean verificarMatch(Pessoa candidato) {
        if (candidato instanceof Candidato) {
            return candidato.getCurtidas().contains(this);
        } else
            throw new RuntimeException("Empresas so dao match com candidatos")
    }

    void adicionarCandidato(Candidato candidato){
        candidatos << candidato
    }


    void curtirCandidato(Candidato candidato) {
        verificarMatch(candidato) ? matches << candidato : curtidas << candidato;
    }
}
