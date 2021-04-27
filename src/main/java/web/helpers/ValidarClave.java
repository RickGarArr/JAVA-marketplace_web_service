package web.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import web.controllers.exceptions.InvalidClaveException;

public abstract class ValidarClave {
    private static final String claveLocal = "77:2f071163ec3ac5fe9edf6a0ab896bd96:01c97375429f6b54daf49bafb27b64be";
    
    public static void validarClave(String clave) throws InvalidClaveException {
        try {
            boolean valida = PasswordAuth.validateString(clave, claveLocal);
            if (!valida) {
                throw new InvalidClaveException("clave es invalida");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
