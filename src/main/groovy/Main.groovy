import DAO.CandidatoDAO
import DAO.CompetenciasDAO
import enums.CompetenciasENUM
import model.Candidato
import model.Competencia

class Main {

    static CompetenciasDAO competenciasDAO = new CompetenciasDAO()
    static CandidatoDAO dao = new CandidatoDAO();

    static void main(String[] args) {

        List<CompetenciasENUM> competencias = new ArrayList<>()

        competencias.addAll(CompetenciasENUM.DJANGO, CompetenciasENUM.PYTHON)

        var test2 = new Candidato()
        test2.setCEP("99950000")
        test2.setCity("tapejara")
        test2.setCPF("fsdfsdfsdghfsdf")
        test2.setEmail("778978drf@gmail")
        test2.setDescription("vamo ")
        test2.setFirst_name("henrique")
        test2.setLast_name("derossi")
        test2.setPassword("senha123")
        test2.setCompetences(competencias)


        dao.listAllCandidatosAndCompetencias()
                .forEach {
                    print(it)
                }
    }
}
