package datos.dao.exceptions;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String msg) {
        super(msg);
    }
}
