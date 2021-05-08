package helpers.validators;

import datos.access.exceptions.GeneralException;
import helpers.validators.exceptions.InvalidParameterValueException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import helpers.validators.exceptions.NullParameterValueException;

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
        if (!errores.equals("")) throw new NullParameterValueException(errores);
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
        if (!errores.equals("")) throw new NullParameterValueException(errores);
        return partValues;
    }
    
    private static boolean validarExtencion(List<String> extValidas, String originalName) {
        String ext = originalName.split("\\.")[originalName.split("\\.").length - 1];
        return !extValidas.contains(ext);
    }
    
    // funcion para validar los parametros de url
    public static Map<String, Object> validarURLParametros(Map<String, Object> URLParameterValues, HttpServletRequest request) throws GeneralException, InvalidParameterValueException {
        // se obtiene el pathInfo // ejemplo "/:parametro1/:parametro2"
        String pathInfo = request.getPathInfo();
        // si el path info es null se arroja una excepcion para mandar el error
        if (pathInfo == null) {
            // se llama al metodo formarPath para crear la ruta esperada
            throw new GeneralException("Error en la peticion, se esperaba: "+ request.getServletPath() +formarPath(URLParameterValues));
        }
        // se elimina el primer "/" y si el pathInfo termina con "/" se elimina este ultimo caracter
        pathInfo = pathInfo.endsWith("/") ? pathInfo.substring(1, pathInfo.length() - 1) : pathInfo.substring(1);
        // se inicializa un arreglo de parametros dividiendo en un arreglo de string la cadena
        String[] parameters = pathInfo.split("/");
        // si el tamaño de los parametros esperados y el tamaño de los parametros generados por split son diferentes
        // se arroja una excepcion para enviarla al cliente
        if (parameters.length != URLParameterValues.size()){
            throw new GeneralException("Error en la peticion, se esperaba: " + request.getServletPath() + formarPath(URLParameterValues));
        }
        
        //se inicia un string para ir concatenando los errores
        String errors = ""; int i = 0;
        for(String key: URLParameterValues.keySet()) {
            if (URLParameterValues.get(key) instanceof Integer) {
                try {
                    URLParameterValues.put(key, Integer.parseInt(parameters[i++]));
                } catch (java.lang.NumberFormatException ex) {
                    errors += errors.equals("") ? "El parametro " + key + " debe ser un numero" : "&El parametro " + key + " debe ser un numero";
                }
            }
            if (URLParameterValues.get(key) instanceof String) {
                URLParameterValues.put(key, parameters[i++]);
            }
            if (URLParameterValues.get(key) instanceof Boolean) {
                URLParameterValues.put(key, Boolean.parseBoolean(parameters[i++]));
            }
            if (URLParameterValues.get(key) instanceof Float) {
                URLParameterValues.put(key, Float.parseFloat(parameters[i++]));
            }
        }
        if (!errors.equals("")) throw new InvalidParameterValueException(errors);
        return URLParameterValues;
    }
    
    private static String formarPath(Map<String, Object> URLParameterValues) {
        String path = "";
            for(String key : URLParameterValues.keySet()) {
                path += "/:" + key;
            }
        return path;
    }
}
