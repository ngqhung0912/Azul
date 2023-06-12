package pppp.group14project.model;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import lombok.Getter;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import javafx.collections.ObservableList;


import java.util.*;

public class Factory {

    /**
     * Used to set the default maximum number of tiles
     * @return the maximum number of tiles
     */
    public int getMaxNumberOfTiles() {
        return 4;
    }

    @Getter
    private ObservableList<Tile> tiles;
    public Factory() {
        this.tiles = FXCollections.observableArrayList();
    }

    /**
     * Grabs the tiles from the factory of one color
     * @param tile the color of the tiles to grab
     * @return a list of tiles
     */
    public List<Tile> grabTiles(Tile tile) throws EmptyException {
        List<Tile> grabList = new ArrayList<>();
        for (Tile t: this.tiles) {
            if (t == tile) {
                grabList.add(t);
            }
        }
        this.tiles.removeAll(grabList);
        return grabList;
    }

    /**
     * Adds tiles from a list to the factory
     * @param tiles the tiles to add to the factory
     * @throws  FullException whether
     */
    public void addTiles(List<Tile> tiles) throws FullException {

        // Throws exception if the tiles cannot be added
        if (this.tiles.size() + tiles.size() > getMaxNumberOfTiles())
            throw new FullException("Can't add more than " + getMaxNumberOfTiles() + " tiles to a factory");

        this.tiles.addAll(tiles);

    }

    /**
     * Used to add a single tile to the factory
     * @param tile The color of the tiles to pick
     */
    public void addTile(Tile tile) throws Exception {

        if (this.tiles.size() + 1 > getMaxNumberOfTiles())
            throw new Exception("Can't add more than " + getMaxNumberOfTiles() + " tiles to a factory");

        this.tiles.add(tile);

    }

    /**
     * Used to see if the factory is empty
     * @return if the factory is empty
     */
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    /**
     * Used to see how many tiles are left in the factory
     * @return the number of tiles in the factory
     */
    public int size() {
        return this.tiles.size();
    }

    public List<Tile> getAllCurrentTiles(){
        return this.tiles;
    }

}
