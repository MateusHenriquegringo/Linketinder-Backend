package model.builder

import model.Empresa

class EmpresaBuilder implements IEmpresaBuilder {

    private Empresa model

    EmpresaBuilder() {
        this.reset()
    }

    @Override
    Empresa build() {
        return this.model
    }

    @Override
    void reset() {
        this.model = new Empresa()
    }

    @Override
    IEmpresaBuilder setId(Long id) {
        model.setId(id)
        return this
    }

    @Override
    IEmpresaBuilder setEmpresaName(String name) {
        model.setEmpresa_name(name)
        return this
    }

    @Override
    IEmpresaBuilder setDescription(String description) {
        model.setDescription(description)
        return this
    }

    @Override
    IEmpresaBuilder setEmail(String email) {
        model.setEmail(email)
        return this
    }

    @Override
    IEmpresaBuilder setCNPJ(String cnpj) {
        model.setCNPJ(cnpj)
        return this
    }

    @Override
    IEmpresaBuilder setCEP(String cep) {
        model.setCEP(cep)
        return this
    }

    @Override
    IEmpresaBuilder setCountry(String country) {
        model.setCountry(country)
        return this
    }

    @Override
    IEmpresaBuilder setPassword(String password) {
        model.setPassword(password)
        return this
    }
}
