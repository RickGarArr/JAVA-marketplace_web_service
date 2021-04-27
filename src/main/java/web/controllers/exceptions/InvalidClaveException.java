package web.controllers.exceptions;
public class InvalidClaveException extends Exception {
    public InvalidClaveException(String msg) {
        super(msg);
    }
}
