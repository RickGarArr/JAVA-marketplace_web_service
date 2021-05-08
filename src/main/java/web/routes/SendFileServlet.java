package web.routes;

import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import web.controllers.AdministradorController;
import helpers.validators.exceptions.NullParameterValueException;
import web.controllers.SendFileController;

@WebServlet(name="SendFile", urlPatterns={"/solicitud/file/*"}, asyncSupported = true)
public class SendFileServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
        case "/solicitud/file": SendFileController.sendFile(request, response); break;
        }
    }
}
