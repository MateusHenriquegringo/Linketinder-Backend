package app.service


import app.model.Vaga
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import app.repository.VagaRepository

@Service
class VagaService {

    @Autowired
    private VagaRepository repository


    void createVaga(Vaga vaga) {
        repository.save(vaga)
    }

    void deleteVaga(Long id) {
        repository.deleteById(id)
    }


    List<Vaga> listAll() {
        return repository.findAll()
    }

    Vaga findById(Long id) {
        return repository.findById(id).orElse(null)
    }


//    List<Vaga> listAllFromEmpresa(Long id){
//
//    }

    void updateVaga(Vaga update) {
        repository.save(update)
    }


}
