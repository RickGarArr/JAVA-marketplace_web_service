package web.helpers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import web.controllers.exceptions.NullParameterValueException;

public abstract class ValidarParametros {
     
    public static Map<String, String> validarParametros(String[] nombres, HttpServletRequest request) throws NullParameterValueException {
        Map<String, String> parameterValues = new HashMap<>();
        String errores = "";
        for(String nombre: nombres) {
            String value = request.getParameter(nombre);
            if( value == null || value.trim().equals("")) {
                errores += errores.equals("") ? "El campo " + nombre + " es un parametro necesario" : "&El campo " + nombre + " es un parametro necesario";
            } else {
                parameterValues.put(nombre, value);
            }
        }
        if (!errores.equals("")) throw new NullParameterValueException(errores);
        return parameterValues;
    }
}
