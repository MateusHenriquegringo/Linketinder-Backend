import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.Assert.assertEquals

class EmpresasCandidatosTest {

    EmpresasCandidatosManager manager = new EmpresasCandidatosManager();

    @BeforeEach
    void setUp() {
        manager.candidatos.clear()
        manager.empresas.clear()
    }
    @Test
    void testAdicionarCandidato(){
        def candidato = new Candidato(nome: 'Carlos Silva', email: 'carlos.silva@example.com', descricao: 'Analista de sistemas.', estado: 'SP', pais: 'Brasil', CEP: '01001-000', CPF: '678.901.234-56', competencias: ['Java', 'SQL'])

        manager.adicionarCandidato(candidato)

        assertEquals(manager.getCandidatos().size(), 1)
        assertEquals(candidato, manager.candidatos[0])
    }

    @Test
    void testAdicionarEmpresa() {
        def empresa = new Empresa(nome: 'TechCorp', email: 'contact@techcorp.com', CNPJ: '67.890.123/0001-45', pais: 'Brasil', estadoFederativo: 'SP', CEP: '11001-000', descricao: 'Empresa de tecnologia inovadora.')

        manager.adicionarEmpresa(empresa)

        assertEquals(1, manager.empresas.size())
        assertEquals(empresa, manager.empresas[0])

        verify(manager, time(1).adicionarEmpresa())
    }
}
