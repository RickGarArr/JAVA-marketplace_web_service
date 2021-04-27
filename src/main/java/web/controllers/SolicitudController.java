package web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SolicitudController {
    public static void createSolicitud(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String[] campos = {"nombre", "email", "telefono"};
    }
}
