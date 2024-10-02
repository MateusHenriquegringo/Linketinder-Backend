package DTO.Response

record VagaResponseDTO(
        long id,
        String name,
        String description,
        String city,
        String state,
        Long empresaID,
        List<String> competences
) {

}