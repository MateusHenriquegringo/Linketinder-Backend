package service.dto

record EmpresaResponseDTO(
        long id,
        String empresa_name,
        String description,
        String email,
        String CNPJ,
        String CEP,
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
        return CNPJ
    }

    String getCEP() {
        return CEP
    }

    String getCountry() {
        return country
    }
}
