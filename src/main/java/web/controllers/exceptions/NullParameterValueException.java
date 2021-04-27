package web.controllers.exceptions;

public class NullParameterValueException extends Exception{
    public NullParameterValueException(String message) {
        super(message);
    }
}
