class Main {
    //Mateus Derossi
    static Scanner scanner = new Scanner(System.in)

    def static empresas = [
            new Empresa(nome: 'JetBrains', email: 'support@jetbrains.com', CNPJ: '12.345.678/0001-90', pais: 'República Tcheca', estadoFederativo: 'Praga', CEP: '11000', descricao: 'Desenvolvedores das ferramentas de IDEs populares como IntelliJ IDEA e PyCharm.'),
            new Empresa(nome: 'Red Hat', email: 'info@redhat.com', CNPJ: '23.456.789/0001-01', pais: 'Estados Unidos', estadoFederativo: 'North Carolina', CEP: '27513', descricao: 'Fornecedor líder de soluções de software open-source e suporte para Linux.'),
            new Empresa(nome: 'Microsoft', email: 'contact@microsoft.com', CNPJ: '34.567.890/0001-12', pais: 'Estados Unidos', estadoFederativo: 'Washington', CEP: '98052', descricao: 'Desenvolvedores do Windows, Visual Studio e Azure.'),
            new Empresa(nome: 'GitHub', email: 'support@github.com', CNPJ: '45.678.901/0001-23', pais: 'Estados Unidos', estadoFederativo: 'California', CEP: '94107', descricao: 'Plataforma de hospedagem de código-fonte e colaboração de software.'),
            new Empresa(nome: 'FinancePro', email: 'support@financepro.com', CNPJ: '56.789.012/0001-34', pais: 'Brasil', estadoFederativo: 'BA', CEP: '05000-000', descricao: 'Consultoria e serviços financeiros de alto nível.')
    ]

    def static candidatos = [
            new Candidato(nome: 'Ana Silva', email: 'ana.silva@example.com', descricao: 'Desenvolvedora de software com 5 anos de experiência.', estado: 'SP', pais: 'Brasil', CEP: '01000-000', CPF: '123.456.789-00', competencias: ['Java', 'Groovy', 'Spring']),
            new Candidato(nome: 'João Pereira', email: 'joao.pereira@example.com', descricao: 'Analista de sistemas com foco em bancos de dados.', estado: 'RJ', pais: 'Brasil', CEP: '20000-000', CPF: '234.567.890-01', competencias: ['SQL', 'Oracle', 'SQL']),
            new Candidato(nome: 'Maria Oliveira', email: 'maria.oliveira@example.com', descricao: 'Especialista em front-end com experiência em React.', estado: 'MG', pais: 'Brasil', CEP: '30000-000', CPF: '345.678.901-23', competencias: ['JavaScript', 'React', 'CSS']),
            new Candidato(nome: 'Pedro Souza', email: 'pedro.souza@example.com', descricao: 'Engenheiro de dados com experiência em ETL.', estado: 'RS', pais: 'Brasil', CEP: '90000-000', CPF: '456.789.012-34', competencias: ['Python', 'Linux', 'Excel']),
            new Candidato(nome: 'Juliana Costa', email: 'juliana.costa@example.com', descricao: 'Gestora de projetos com certificação PMP.', estado: 'BA', pais: 'Brasil', CEP: '40000-000', CPF: '567.890.123-45', competencias: ['Gestão de Projetos', 'Java', 'Scrum'])
    ]

    def static listarCandidatos() {
        candidatos.each {
            println("${it.getNome()}, ${it.getEndereco()}, ${it.getCompetencias()}, ${it.getCPF()}")
        }
    }

    def static listarEmpresas() {
        empresas.each {
            println("${it.getNome()}, ${it.getEndereco()}, ${it.getCNPJ()}")
        }
    }
    static void main(String[] args) {

        while(true) {
            println("""
                     Digite 1 para listar candidatos
                     2 para listar empresas
                     0 para sair""")

            String input = scanner.nextLine()
        switch (input) {
            case "1":
                listarCandidatos()
                break
            case "2":
                listarEmpresas()
                break
            case "0":
                println("encerrando")
                return
            default:
                "digite um numero valido"
        }
    }

}
}