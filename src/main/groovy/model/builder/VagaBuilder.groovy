package model.builder

import enums.CompetenciaENUM
import model.Vaga

class VagaBuilder implements IVagaBuilder{

    Vaga model;

    VagaBuilder(){
        this.reset()
    }

    @Override
    Vaga build() {
        return model
    }

    @Override
    void reset() {
        this.model = new Vaga()
    }

    @Override
    IVagaBuilder setId(Long id) {
        model.setId(id)
        return this
    }

    @Override
    IVagaBuilder setVagaName(String name) {
        model.setVaga_name(name)
        return this
    }

    @Override
    IVagaBuilder setDescription(String description) {
        model.setDescription(description)
        return this
    }

    @Override
    IVagaBuilder setCity(String city) {
        model.setCity(city)
        return this
    }

    @Override
    IVagaBuilder setState(String state) {
        model.setState(state)
        return this
    }

    @Override
    IVagaBuilder setEmpresaID(Long empresaId) {
        model.setEmpresaId(empresaId)
        return this
    }


    @Override
    IVagaBuilder setCompetences(List<CompetenciaENUM> competences) {
        model.setCompetences(competences)
        return this
    }
}
