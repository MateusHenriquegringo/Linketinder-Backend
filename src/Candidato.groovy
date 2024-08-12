class Candidato implements Pessoa {

    String nome
    String email
    String descricao
    String estado
    String pais
    String CEP
    String CPF

    List<Empresa> matches
    List<Empresa> curtidas
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

    boolean verificarMatch(Empresa empresa) {
        return empresa.getCurtidas().contains(this)
    }

    void curtirEmpresa(Empresa empresa) {
        if (verificarMatch(empresa)) {
            if (!matches.contains(empresa)) {
                matches << empresa
            }
        } else {
            if (!curtidas.contains(empresa)) {
                curtidas << empresa
            }
        }
    }
}

