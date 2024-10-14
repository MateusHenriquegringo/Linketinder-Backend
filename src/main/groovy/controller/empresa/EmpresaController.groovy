package controller.empresa

import com.fasterxml.jackson.databind.ObjectMapper
import controller.util.Tools
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Empresa
import service.EmpresaService

@WebServlet("/empresa/*")
class EmpresaController extends HttpServlet {

    private final EmpresaService service = new EmpresaService()
    private final ObjectMapper mapper = new ObjectMapper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if(id != null){
            mapper.writeValue(resp.getWriter(), service.findEmpresaById(id))
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class)
            service.updateEmpresa(empresa, id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Tools.extractIdFromPath(req.getPathInfo())
        if (id != null) {
            service.deleteEmpresa(id)
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }
    }

}
