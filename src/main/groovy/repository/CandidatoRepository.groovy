package repository


import model.Candidato
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    //List<Candidato> listAllWithoutCompetences() {}

    //void removeCompetence(Long id, CompetenciaENUM competence) {

    //void addCompetence(Long id, List<CompetenciaENUM> competences) {

}
