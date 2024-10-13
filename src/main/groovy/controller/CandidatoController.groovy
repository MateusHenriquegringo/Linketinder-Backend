package controller

import com.fasterxml.jackson.databind.ObjectMapper
import model.Candidato
import service.CandidatoService
import service.dto.CandidatoResponseDTO

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/candidato")
class CandidatoController extends HttpServlet {

    private final CandidatoService service = new CandidatoService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo()

        if (pathInfo == null || pathInfo.equals("/")) {
            List<CandidatoResponseDTO> candidatos = service.listAll()
            sendResponse(resp, candidatos)
        } else {
            Long id = extractIdFromPath(pathInfo)
            if (id != null) {
                CandidatoResponseDTO candidato = service.findCandidatoById(id)
                if (candidato != null) {
                    sendResponse(resp, candidato)
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidato candidato = mapper.readValue(req.getInputStream(), Candidato.class)
        service.createCandidato(candidato)
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = extractIdFromPath(req.getPathInfo())
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
        Long id = extractIdFromPath(req.getPathInfo())
        if (id != null) {
            service.deleteCandidato(id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

    private Long extractIdFromPath(String pathInfo) {
        try {
            String[] parts = pathInfo.split("/")
            return Long.parseLong(parts[1])
        } catch (Exception e) {
            return null
        }
    }

    private void sendResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        mapper.writeValue(resp.getWriter(), data)
    }

}
