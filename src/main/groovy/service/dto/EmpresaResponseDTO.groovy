package service.dto

record EmpresaResponseDTO(
        long id,
        String empresa_name,
        String description,
        String email,
        String cnpj,
        String cep,
        String country
) {
    long getId() {
        return id
    }

    String getEmpresa_name() {
        return empresa_name
    }

    String getDescription() {
        return description
    }

    String getEmail() {
        return email
    }

    String getCNPJ() {
        return cnpj
    }

    String getCEP() {
        return cep
    }

    String getCountry() {
        return country
    }
}
