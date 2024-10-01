package DTO.Response

record VagaResponseDTO(
        long id,
        String name,
        String description,
        String city,
        String state,
        long empresaID,
        List<String> competences
) {

}