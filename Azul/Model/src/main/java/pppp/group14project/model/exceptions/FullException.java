package pppp.group14project.model.exceptions;

/**
 * Used to indicate a particular container is full
 */
public class FullException extends Exception {

    public FullException() {}

    public FullException(String msg)
    {
        super(msg);
    }

}
