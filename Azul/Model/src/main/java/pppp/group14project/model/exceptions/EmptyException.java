package pppp.group14project.model.exceptions;

/**
 * Used to indicate a particular container is empty
 */
public class EmptyException extends Exception {

    public EmptyException() {}

    public EmptyException(String msg)
    {
        super(msg);
    }

}
