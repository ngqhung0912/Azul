package pppp.group14project.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.Setter;
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
    @Setter
    private ObservableList<Tile> tiles;

    @Getter
    private SimpleStringProperty selected_colour;

    public Factory() {
        this.tiles = FXCollections.observableArrayList();
        this.selected_colour = new SimpleStringProperty();
    }

    /**
     * Checks if the passed tile exist in the factory,
     * and sets the selected_colour to this tile's colour
     * if it is.
     * @param tile the selected tile
     */
    public void grabTiles(Tile tile) {
        if(this.tiles.contains(tile)) {
            this.selected_colour.set(tile.toString());
    }

    public List<Tile> removeTiles(Tile tile){
        ObservableList<Tile> grabList = FXCollections.observableArrayList();
        ObservableList<Tile> newTileList = FXCollections.observableArrayList(tiles);
        for (Tile t: this.tiles) {
            if (t == tile) {
                grabList.add(t);
                newTileList.remove(t);
            }
        }
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

    /**
     * Counts how many tiles of a certain colour are on the factory
     * @param tile the colour to count
     * @return the number of tiles of the given colour
     */
    public int countColour(Tile tile) {
        int counter = 0;
        for(Tile t: this.tiles) {
            if (t == tile) {
                counter++;
            }
        }
        return counter;
    }

    public void empty() {
        this.selected_colour = new SimpleStringProperty();
        this.tiles.clear();
    }

        public List<Tile> getAllCurrentTiles(){
            return this.tiles;
        }

}
