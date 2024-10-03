package model.builder

import model.Empresa

interface IEmpresaBuilder extends IBuilder<Empresa>{
    IEmpresaBuilder setId(Long id);
    IEmpresaBuilder setEmpresaName(String name);
    IEmpresaBuilder setDescription(String description);
    IEmpresaBuilder setEmail(String email);
    IEmpresaBuilder setCNPJ(String cnpj);
    IEmpresaBuilder setCEP(String cep);
    IEmpresaBuilder setCountry(String country);
    IEmpresaBuilder setPassword(String password);

}