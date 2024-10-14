package controller.candidato

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Candidato
import service.CandidatoService
import service.dto.CandidatoResponseDTO

@WebServlet("/candidato")
class AllCandidatosController extends HttpServlet {

    private final CandidatoService service = new CandidatoService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidato candidato = mapper.readValue(req.getInputStream(), Candidato.class)
        service.createCandidato(candidato)
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CandidatoResponseDTO> responseList = service.listAll()
        mapper.writeValue(resp.getWriter(), responseList)
        resp.setStatus(HttpServletResponse.SC_OK)
    }

}
