package model.builder

import enums.CompetenciaENUM
import model.Candidato

class CandidatoBuilder implements ICandidatoBuilder {

    Candidato model

    CandidatoBuilder() {
        this.reset()
    }


    @Override
    void reset() {
        model = new Candidato()
    }

    @Override
    CandidatoBuilder setFirstName(String firstName) {
        model.setFirst_name(firstName)
        return this
    }

    @Override
    CandidatoBuilder setLastName(String lastName) {
        model.setLast_name(lastName)
        return this
    }

    @Override
    CandidatoBuilder setCPF(String cpf) {
        model.setCPF(cpf)
        return this
    }

    @Override
    CandidatoBuilder setDescription(String description) {
        model.setDescription(description)
        return this
    }

    @Override
    CandidatoBuilder setCEP(String cep) {
        model.setCEP(cep)
        return this
    }

    @Override
    CandidatoBuilder setCity(String city) {
        model.setCity(city)
        return this
    }

    @Override
    CandidatoBuilder setId(Long id) {
        model.setId(id)
        return this
    }

    @Override
    CandidatoBuilder setEmail(String email) {
        model.setEmail(email)
        return this
    }

    @Override
    CandidatoBuilder setPassword(String password) {
        model.setPassword(password)
        return this
    }

    @Override
    CandidatoBuilder setCompetences(List<CompetenciaENUM> competences) {
        model.setCompetences(competences)
        return this
    }

    @Override
    Candidato build() {
        return model
    }
}
