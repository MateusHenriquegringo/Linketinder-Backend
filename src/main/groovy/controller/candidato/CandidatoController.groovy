package controller.candidato

import com.fasterxml.jackson.databind.ObjectMapper
import controller.util.Tools
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Candidato
import service.CandidatoService

@WebServlet("/candidato/*")
class CandidatoController extends HttpServlet {

    private CandidatoService service = new CandidatoService()
    private ObjectMapper mapper = new ObjectMapper()


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo()
        Long id = Tools.extractIdFromPath(pathInfo)
        if (id != null) {
            resp.setStatus(HttpServletResponse.SC_OK)
            mapper.writeValue(resp.getWriter(), service.findCandidatoById(id))
        }  else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            Candidato candidato = mapper.readValue(req.getInputStream(), Candidato.class)
            service.updateCandidato(candidato, id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            service.deleteCandidato(id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

}
