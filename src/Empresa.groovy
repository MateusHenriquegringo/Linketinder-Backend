class Empresa implements Pessoa {

    String nome
    String email
    String CNPJ
    String pais
    String estadoFederativo
    String CEP
    String descricao

    List<Candidato> candidatos

    @Override
    String getEndereco() {
        return "${this.CEP}, ${this.pais}, ${this.estadoFederativo}"
    }


    void adicionarCandidato(Candidato candidato){
        candidatos << candidato
    }

}
