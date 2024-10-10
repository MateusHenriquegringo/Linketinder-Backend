package service

import model.Empresa
import repository.EmpresaRepository
import service.dto.EmpresaResponseDTO
import spock.lang.Specification
import spock.lang.Subject

class EmpresaServiceTest extends Specification {

    EmpresaRepository repository = Mock(EmpresaRepository)

    @Subject
    EmpresaService empresaService = new EmpresaService(repository)

    def "should create an Empresa"() {
        given:
        Empresa empresa = new Empresa(
                id: 1L,
                empresa_name: "Tech Solutions",
                email: "info@techsolutions.com",
                CNPJ: "12345678000195",
                CEP: "12345678",
                country: "Brazil"
        )

        when:
        empresaService.createEmpresa(empresa)

        then:
        1 * repository.createEmpresa(empresa)
    }

    def "should delete an Empresa by id"() {
        given:
        Long id = 1L

        when:
        empresaService.deleteEmpresa(id)

        then:
        1 * repository.deleteEmpresa(id)
    }

    def "should find Empresa by id and map to EmpresaResponseDTO"() {
        given:
        Empresa empresa = new Empresa(
                id: 1L,
                empresa_name: "Tech Solutions",
                email: "info@techsolutions.com",
                CNPJ: "12345678000195",
                CEP: "12345678",
                country: "Brazil"
        )

        repository.findEmpresaById(1L) >> empresa

        when:
        EmpresaResponseDTO result = empresaService.findEmpresaById(1L)

        then:
        result.getId() == empresa.getId()
        result.getEmpresa_name() == empresa.getEmpresa_name()
        result.getEmail() == empresa.getEmail()
        result.getCNPJ() == empresa.getCNPJ()
        result.getCEP() == empresa.getCEP()
        result.getCountry() == empresa.getCountry()
    }

    def "should list all Empresas and map to EmpresaResponseDTO"() {
        given:
        List<Empresa> empresas = [
                new Empresa(id: 1L, empresa_name: "Tech Solutions", email: "info@techsolutions.com",
                        CNPJ: "12345678000195", CEP: "12345678", country: "Brazil"),
                new Empresa(id: 2L, empresa_name: "Innovative Corp", email: "contact@innovativecorp.com",
                        CNPJ: "98765432000196", CEP: "87654321", country: "Brazil")
        ]

        repository.listAll() >> empresas

        when:
        List<EmpresaResponseDTO> results = empresaService.listAll()

        then:
        results.size() == 2
        results[0].getId() == empresas[0].getId()
        results[0].getEmpresa_name() == empresas[0].getEmpresa_name()
        results[1].getId() == empresas[1].getId()
        results[1].getEmpresa_name() == empresas[1].getEmpresa_name()
    }

    def "should update an Empresa"() {
        given:
        Empresa empresa = new Empresa(
                id: 1L,
                empresa_name: "Tech Solutions",
                email: "info@techsolutions.com",
                CNPJ: "12345678000195",
                CEP: "12345678",
                country: "Brazil"
        )
        Long id = 1L

        when:
        empresaService.updateEmpresa(empresa, id)

        then:
        1 * repository.updateEmpresa(empresa, id)
    }
}
