package helpers;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerarNombreUnico{
    
    public static String generarNombreUnico() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] bytesName = new byte[16];
        sr.nextBytes (bytesName);
        BigInteger bi = new BigInteger(1, bytesName);
        String hex = bi.toString(16);
        String name;
        int paddingLength = (bytesName.length * 2) - hex.length();
        if(paddingLength > 0) {
            name = String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            name = hex;
        }
        return name;
    }
}
    

