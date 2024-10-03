package model.builder

import enums.CompetenciaENUM
import model.Vaga

interface IVagaBuilder extends IBuilder<Vaga> {

    IVagaBuilder setId(Long id);

    IVagaBuilder setVagaName(String name);

    IVagaBuilder setDescription(String description)

    IVagaBuilder setCity(String city)

    IVagaBuilder setState(String state);

    IVagaBuilder setEmpresaID(Long empresaId);

    IVagaBuilder setCompetences(List<CompetenciaENUM> competences)
}
