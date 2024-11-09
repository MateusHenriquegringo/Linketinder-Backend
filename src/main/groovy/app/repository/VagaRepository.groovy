package app.repository


import app.model.Vaga
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VagaRepository extends JpaRepository<Vaga, Long> {
    // List<Vaga> listAllFromEmpresa(Long id) {

}


