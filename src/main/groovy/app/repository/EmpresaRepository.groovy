package app.repository


import app.model.Empresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository extends JpaRepository<Empresa, Long> {


}
