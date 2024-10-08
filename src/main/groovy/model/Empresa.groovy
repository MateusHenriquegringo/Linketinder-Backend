package model

class Empresa {

    long id

    String empresa_name
    String description
    String email
    String CNPJ
    String CEP
    String country
    String password

    Empresa () {}

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", empresa_name='" + empresa_name + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", CEP='" + CEP + '\'' +
                ", country='" + country + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
