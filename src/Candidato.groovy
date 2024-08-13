class Candidato implements Pessoa {

    String nome
    String email
    String descricao
    String estado
    String pais
    String CEP
    String CPF

    Set<Empresa> matches
    Set<Empresa> curtidas
    List<String> competencias

    @Override
    String getEndereco() {
        return "${this.CEP}, ${this.pais}, ${this.estado}"

    }

    void adicionarCompetencia(String competencia) {
        competencias << competencia
    }

    void listarCompetencias() {
        println "Competências de ${nome}: ${competencias.join(', ')}"
    }

    boolean verificarMatch(Pessoa empresa) {
        if (empresa instanceof Empresa) {
            return empresa.getCurtidas().contains(this);
        } else
            throw new RuntimeException("Candidatos so dao match com empresas")    }

    void curtirEmpresa(Empresa empresa) {
        verificarMatch(empresa) ? matches << empresa : curtidas << empresa;
    }
}

