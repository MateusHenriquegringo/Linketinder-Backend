
class Candidato {

    Long id

    String first_name
    String last_name
    String email
    String description
    String CEP
    String city

    Candidato(String password, String CPF, String city, String CEP, String description, String email, String last_name, String first_name, List<String> competencies) {
        this.password = password
        this.CPF = CPF
        this.city = city
        this.CEP = CEP
        this.description = description
        this.email = email
        this.last_name = last_name
        this.first_name = first_name
        this.competencies = competencies
    }

    Candidato(String first_name, String last_name, String email, String description, String CEP, String city, String CPF, String password) {
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.description = description
        this.CEP = CEP
        this.city = city
        this.CPF = CPF
        this.password = password
    }
    String CPF
    String password

    Set<Empresa> matches
    Set<Empresa> likes
    List<String> competencies

    void adicionarCompetencia(String competencia) {
        competencias << competencia
    }

    void listarCompetencias() {
        println "Competências de ${nome}: ${competencias.join(', ')}"
    }

    Long getId() {
        return id
    }

    String getCity() {
        return city
    }

    List<String> getCompetencies() {
        return competencies
    }

    boolean verificarMatch(Pessoa empresa) {
        if (empresa instanceof Empresa) {
            return empresa.getCurtidas().contains(this);
        } else
            throw new RuntimeException("Candidatos so dao match com empresas")    }

    void curtirEmpresa(Empresa empresa) {
        verificarMatch(empresa) ? matches << empresa : curtidas << empresa;
    }


    String getFirst_name() {
        return first_name
    }

    String getLast_name() {
        return last_name
    }

    String getEmail() {
        return email
    }

    String getDescription() {
        return description
    }

    String getCEP() {
        return CEP
    }

    String getCPF() {
        return CPF
    }

    String getPassword() {
        return password
    }
    void setFirst_name(String first_name) {
        this.first_name = first_name
    }

    void setLast_name(String last_name) {
        this.last_name = last_name
    }

    void setEmail(String email) {
        this.email = email
    }

    void setDescription(String description) {
        this.description = description
    }

    void setState(String state) {
        this.state = state
    }

    void setCEP(String CEP) {
        this.CEP = CEP
    }

    void setCity(String city) {
        this.city = city
    }

    void setCPF(String CPF) {
        this.CPF = CPF
    }

    void setPassword(String password) {
        this.password = password
    }
}

