package web.controllers;

import helpers.validators.exceptions.InvalidClaveException;
import helpers.validators.exceptions.NullParameterValueException;
import helpers.response.SendMessage;
import helpers.validators.PasswordAuth;
import helpers.validators.ValidarPattern;
import helpers.validators.ValidarClave;
import helpers.validators.ValidarParametros;
import datos.models.UsuarioModel;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import javax.json.*;
import javax.servlet.http.*;
import web.helpers.exceptions.*;
import datos.access.*;
import datos.access.exceptions.DuplicateEntryException;

public class AdministradorController {

    public static void createAdmin(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String[] campos = {"email", "telefono", "password", "clave"};
        try {
            Map<String, String> parameterValues = ValidarParametros.validarParametros(campos, request);
            ValidarClave.validarClave(parameterValues.get("clave"));
            ValidarPattern.validarEmail(parameterValues.get("email"));
            ValidarPattern.validarTelefono(parameterValues.get("telefono"));
            String passwordHash = PasswordAuth.generateHash(parameterValues.get("password"));
            UsuarioModel admin = new UsuarioModel(
                    parameterValues.get("email"),
                    parameterValues.get("telefono"),
                    passwordHash,
                    "admin");
            UsuarioAccess usuarioAccess = new UsuarioAccess();
            int result = usuarioAccess.insertUsuario(admin);
            Writer writer = new StringWriter();
            JsonObjectBuilder ob = Json.createObjectBuilder();
            ob.add("ok", true);
            ob.add("rows", result);
            Json.createWriter(writer).writeObject(ob.build());
            PrintWriter out = response.getWriter();
            out.print(writer.toString());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace(System.out);
        } catch (NullParameterValueException | InvalidClaveException | InvalidPatternException | DuplicateEntryException ex) {
            SendMessage.sendErrors(response, Arrays.asList(ex.getMessage()));
        }
    }
}
