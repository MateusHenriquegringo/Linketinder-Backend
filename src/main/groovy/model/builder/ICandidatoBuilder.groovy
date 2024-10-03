package model.builder

import enums.CompetenciaENUM
import model.Candidato

interface ICandidatoBuilder extends IBuilder<Candidato> {

    ICandidatoBuilder setFirstName(String firstName);

    ICandidatoBuilder setLastName(String lastName);

    ICandidatoBuilder setCPF(String cpf);

    ICandidatoBuilder setDescription(String description);

    ICandidatoBuilder setCEP(String cep);

    ICandidatoBuilder setCity(String city);

    ICandidatoBuilder setId(Long id);

    ICandidatoBuilder setEmail(String email);

    ICandidatoBuilder setPassword(String id);

    ICandidatoBuilder setCompetences(List<CompetenciaENUM> competences);

}
