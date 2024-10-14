package controller.empresa

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Empresa
import service.EmpresaService
import service.dto.EmpresaResponseDTO

@WebServlet("/empresa")
class AllEmpresasController extends HttpServlet {

    private final EmpresaService service = new EmpresaService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class)
        service.createEmpresa(empresa)
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<EmpresaResponseDTO> responseList = service.listAll()
        mapper.writeValue(resp.getWriter(), responseList)
        resp.setStatus(HttpServletResponse.SC_OK)
    }

}
