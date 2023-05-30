package pppp.group14project.model;

import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLine {

    private List<Tile> tiles;

    public PatternLine(int numberOfTiles) {
        // Constant number of elements
        this.tiles = new ArrayList<>(Collections.nCopies(numberOfTiles, null));
    }

    /**
     * Adds tiles to the PatternLine, returns the excess tiles to add to the floor
     * @param t the tiles to add
     * @return the excess tiles
     */
    public List<Tile> addTiles(List<Tile> t) throws WrongTileException {
        return null;
    }

    /**
     * Empties the PatternLine, and returns a Tile to be placed on the Wall
     * @return the Tile to be moved to the Wall
     */
    public Tile moveTiles() {
        return null;
    }

    /**
     * Returns the color of the PatternLine, null if no tiles are in the PatternLine
     * @return
     */
    public Tile getColor() {
        return null;
    }

    /**
     * Empties the PatternLine
     */
    public void empty() {
    }

    /**
     * Used to see if the PatternLine is full
     * @return if the PatternLine is full
     */
    public boolean isFull() {
        return false;
    }

}
