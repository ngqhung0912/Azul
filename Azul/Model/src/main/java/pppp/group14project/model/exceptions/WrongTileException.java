package pppp.group14project.model.exceptions;

/**
 * Used to indicate a wrong Tile has been provided
 */
public class WrongTileException extends Exception {

    public WrongTileException() {}

    public WrongTileException(String msg)
    {
        super(msg);
    }

}
