package DTO.Request

record EmpresaRequestDTO(
        String empresa_name,
        String description,
        String email,
        String cnpj,
        String cep,
        String country,
        String password
) {

}