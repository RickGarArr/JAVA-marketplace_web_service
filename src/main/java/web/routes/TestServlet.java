package web.routes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(name = "testServlet", urlPatterns = {"/test1", "/test2/*", "/test3/{id}"})
public class TestServlet extends HttpServlet {
    public void proccessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cadena = "ricarod";
        String[] cadenas = cadena.split("a");
        System.out.println(Arrays.toString(cadenas));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.proccessRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.proccessRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
