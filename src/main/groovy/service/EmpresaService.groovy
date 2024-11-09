package service

import model.Empresa
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import repository.EmpresaRepository

@Service
class EmpresaService {

    @Autowired
    private EmpresaRepository repository


    void createEmpresa(Empresa empresa) {
        repository.save(empresa)
    }

    void deleteEmpresa(Long id) {
        repository.deleteById(id)
    }

    Empresa findEmpresaById(Long id) {
        repository.findById(id).orElse(null)
    }

    List<Empresa> listAll() {
        return repository.findAll()
    }

    void updateEmpresa(Empresa empresa) {
        repository.save(empresa)
    }


    void likeCandidato(long id, long idVaga) {
        mediator.likeFromEmpresaToCandidato(id, idVaga)

    }

    void dislikeCandidato(long id, long idVaga) {
        mediator.dislikeFromEmpresaToCandidato(id, idVaga)
    }


}

