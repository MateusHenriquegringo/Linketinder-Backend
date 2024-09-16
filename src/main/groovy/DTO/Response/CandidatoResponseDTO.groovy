package DTO.Response

record CandidatoResponseDTO(
        long id,
        String first_name,
        String last_name,
        String CPF,
        String description,
        String email,
        String CEP,
        String city
) {
    public static class Builder {
        private long id;
        private String first_name;
        private String last_name;
        private String CPF;
        private String description;
        private String email;
        private String CEP;
        private String city;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public Builder lastName(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public Builder CPF(String CPF) {
            this.CPF = CPF;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder CEP(String CEP) {
            this.CEP = CEP;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public CandidatoResponseDTO build() {
            return new CandidatoResponseDTO(id, first_name, last_name, CPF, description, email, CEP, city);
        }
    }
}