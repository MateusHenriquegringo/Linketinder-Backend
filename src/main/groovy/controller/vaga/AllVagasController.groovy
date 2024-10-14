package controller.vaga

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Vaga
import service.VagaService
import service.dto.VagaResponseDTO

@WebServlet("/vaga")
class AllVagasController extends HttpServlet {

    private final VagaService service = new VagaService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vaga vaga = mapper.readValue(req.getInputStream(), Vaga.class)
        service.createVaga(vaga)
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VagaResponseDTO> responseList = service.listAll()
        mapper.writeValue(resp.getWriter(), responseList)
        resp.setStatus(HttpServletResponse.SC_OK)
    }

}
