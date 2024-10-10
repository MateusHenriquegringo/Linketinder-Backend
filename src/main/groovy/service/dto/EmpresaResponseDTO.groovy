package service.dto

class EmpresaResponseDTO {

    long id
    String empresa_name
    String description
    String email
    String CNPJ
    String CEP
    String country

    EmpresaResponseDTO(long id, String empresa_name, String description, String email, String CNPJ, String CEP, String country) {
        this.id = id
        this.empresa_name = empresa_name
        this.description = description
        this.email = email
        this.CNPJ = CNPJ
        this.CEP = CEP
        this.country = country
    }
}
