package model.builder

import model.Empresa

class EmpresaBuilder implements IEmpresaBuilder {

    private Empresa model

    EmpresaBuilder() {
        this.reset()
    }

    @Override
    Empresa build() {
        return model
    }

    @Override
    void reset() {
        this.model = new Empresa()
    }

    @Override
    EmpresaBuilder setId(Long id) {
        model.setId(id)
        return this
    }

    @Override
    EmpresaBuilder setEmpresaName(String name) {
        model.setEmpresa_name(name)
        return this
    }

    @Override
    EmpresaBuilder setDescription(String description) {
        model.setDescription(description)
        return this
    }

    @Override
    EmpresaBuilder setEmail(String email) {
        model.setEmail(email)
        return this
    }

    @Override
    EmpresaBuilder setCNPJ(String cnpj) {
        model.setCNPJ(cnpj)
        return this
    }

    @Override
    EmpresaBuilder setCEP(String cep) {
        model.setCEP(cep)
        return this
    }

    @Override
    EmpresaBuilder setCountry(String country) {
        model.setCountry(country)
        return this
    }

    @Override
    EmpresaBuilder setPassword(String password) {
        model.setPassword(password)
        return this
    }
}
