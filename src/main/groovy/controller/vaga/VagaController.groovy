package controller.vaga

import com.fasterxml.jackson.databind.ObjectMapper
import controller.util.Tools
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Candidato
import model.Vaga
import service.CandidatoService
import service.VagaService

@WebServlet("/vaga/*")
class VagaController extends HttpServlet{

    private final VagaService service = new VagaService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo()
        Long id = Tools.extractIdFromPath(pathInfo)
        if (id != null) {
            mapper.writeValue(resp.getWriter(), service.findById(id))
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            Vaga vaga = mapper.readValue(req.getInputStream(), Vaga.class)
            service.updateVaga(vaga, id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            service.deleteVaga(id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

}
