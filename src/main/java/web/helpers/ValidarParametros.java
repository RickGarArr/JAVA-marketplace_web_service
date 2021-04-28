package web.helpers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import web.controllers.exceptions.NullParameterValueException;

public abstract class ValidarParametros {

    public static Map<String, String> validarParametros(String[] nombres, HttpServletRequest request) throws NullParameterValueException {
        Map<String, String> parameterValues = new HashMap<>();
        String errores = "";
        for (String nombre : nombres) {
            String value = request.getParameter(nombre);
            if (value == null || value.trim().equals("")) {
                errores += errores.equals("") ? "El campo " + nombre + " es un parametro necesario" : "&El campo " + nombre + " es un parametro necesario";
            } else {
                parameterValues.put(nombre, value);
            }
        }
        if (!errores.equals("")) {
            throw new NullParameterValueException(errores);
        }
        return parameterValues;
    }

    public static Map<String, Part> validarPartes(String[] nombres, HttpServletRequest request) throws ServletException, IOException, NullParameterValueException {
        Map<String, Part> partValues = new HashMap<>();
        String errores = "";
        for (String nombre : nombres) {
            Part part = request.getPart(nombre);
            if(part == null) {
                errores += errores.equals("") ? "El campo " + nombre + " es un parametro necesario" : "&El campo " + nombre + " es un parametro necesario";
            } else {
                partValues.put(nombre, part);
            }
        }
        if (!errores.equals("")) {
            throw new NullParameterValueException(errores);
        }
        return partValues;
    }
    
    public static Map<String, Part> validarPartes(String[] nombres, String[] extencionesValidas, HttpServletRequest request) throws NullParameterValueException {
        Map<String, Part> partValues = new HashMap<>();
        String errores = "";
        for (String nombre : nombres) {
            Part part = null;
            try {
                part = request.getPart(nombre);
            } catch(IOException | ServletException ex) {
                throw new NullParameterValueException("Problemas al subir archivo " + nombre + " intente de nuevo");
            }
            if(part == null || part.getSize() == 0) {
                errores += errores.equals("") ? "El campo " + nombre + " es un parametro necesario" : "&El campo " + nombre + " es un parametro necesario";
            } else if (validarExtencion(Arrays.asList(extencionesValidas), part.getSubmittedFileName())){
                errores += errores.equals("") ? "La extencion del archivo " + nombre + " no es valida" : "&La extencion del archivo " + nombre + " no es valida";
            } else {
                partValues.put(nombre, part);
            }
        }
        if (!errores.equals("")) {
            throw new NullParameterValueException(errores);
        }
        return partValues;
    }
    
    private static boolean validarExtencion(List<String> extValidas, String originalName) {
        String ext = originalName.split("\\.")[originalName.split("\\.").length - 1];
        return !extValidas.contains(ext);
    }
}
