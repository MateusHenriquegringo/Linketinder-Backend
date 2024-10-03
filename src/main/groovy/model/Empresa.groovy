package model

import DTO.Request.EmpresaRequestDTO

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

    Empresa(EmpresaRequestDTO dto) {
        this.empresa_name = dto.empresa_name()
        this.description = dto.description()
        this.email = dto.email()
        this.CNPJ = dto.cnpj()
        this.CEP = dto.cep()
        this.country = dto.country()
        this.password = dto.password()
    }

}
