package web.routes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import web.controllers.SolicitudController;
import java.util.Arrays;
import helpers.response.SendMessage;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
  maxFileSize = 1024 * 1024 * 5, 
  maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "Solicitud", urlPatterns = "/solicitud/create")
public class SolicitudServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        switch (request.getServletPath()){
            case "/solicitud/create": {
                try {
                    SolicitudController.createSolicitud(request, response);
                } catch (ServletException ex) {
                    SendMessage.sendErrors(response, Arrays.asList(ex.getMessage()));
                }
            }

        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
