package pppp.group14project.model;

import pppp.group14project.model.exceptions.NotFullException;
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
        // Throw exception if not all Tiles given are same color, or do not match the ones already in the list
        boolean sameTilesInInput = Collections.frequency(t, t.get(0)) == t.size();
        boolean sameTilesInputAndOutput = Collections.frequency(tiles, t.get(0)) > 0 || isEmpty();
        if (!sameTilesInInput || !sameTilesInputAndOutput)
            throw new WrongTileException("Given tiles do not match");


        List<Tile> returnedTiles = new ArrayList(t);
        // Moves Tiles from t to tiles
        for (int i = 0; i < tiles.size(); i++) {
            if (returnedTiles.size() == 0)
                return null;
            if (tiles.get(i) == null)
                tiles.set(i, returnedTiles.remove(0));
        }
        return returnedTiles;
    }

    /**
     * Empties the PatternLine, and returns a Tile to be placed on the Wall
     * @return the Tile to be moved to the Wall
     */
    public Tile moveTiles() throws NotFullException {
        if (!isFull())
            throw new NotFullException("The PatternLine is not yet full");
        Tile tileToReturn = tiles.get(0);
        empty();
        return tileToReturn;
    }

    /**
     * Returns the color of the PatternLine, null if no tiles are in the PatternLine
     * @return
     */
    public Tile getColor() {
        return tiles.get(0);
    }

    /**
     * Empties the PatternLine
     */
    public void empty() {
        this.tiles = new ArrayList<>(Collections.nCopies(tiles.size(), null));
    }

    /**
     * Used to see if the PatternLine is full
     * @return if the PatternLine is full
     */
    public boolean isFull() {
        return Collections.frequency(tiles, tiles.get(0)) == tiles.size() && tiles.get(0) != null;
    }

    /**
     * Used to see if the PatternLine is empty
     * @return if the PatternLine is empty
     */
    public boolean isEmpty() {
        return Collections.frequency(tiles, null) == tiles.size();
    }

}
