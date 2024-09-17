package DTO.Response

record VagaCompetenciasDTO(
        long id,
        String name,
        String description,
        String city,
        String state,
        long empresaID,
        List<String> competences
) {

}