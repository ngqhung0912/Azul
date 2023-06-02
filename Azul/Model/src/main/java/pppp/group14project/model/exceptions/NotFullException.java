package pppp.group14project.model.exceptions;

/**
 * Used to indicate a particular container is not full
 */
public class NotFullException extends Exception {

    public NotFullException() {}

    public NotFullException(String msg)
    {
        super(msg);
    }

}
