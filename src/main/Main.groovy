import DAO.CandidatoDAO
import model.Candidato

class Main {

    static CandidatoDAO dao = new CandidatoDAO();

    static void main(String[] args) {

        var test = new Candidato()
        test.setCEP("99950000")
        test.setCity("tapejara")
        test.setCPF("04155784009")
        test.setEmail("henrique@gmail")
        test.setDescription("vamo ")
        test.setFirst_name("mateus")
        test.setLast_name("derossi")

        dao.createCandidato(test)

    }
}