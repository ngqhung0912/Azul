package pppp.group14project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.exceptions.FullException;


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


    public Factory() {
        this.tiles = FXCollections.observableArrayList();
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

    public void empty() {
        this.tiles.clear();
    }

    public List<Tile> getAllCurrentTiles(){
            return this.tiles;
        }


    public List<List<Tile>> grabTiles(Tile tile) {
        List<Tile> grabTiles = new ArrayList<>();
        List<Tile> tableTiles = new ArrayList<>();

        // Add the starting Tile if it contains it
        if (tiles.contains(Tile.STARTING)){
            grabTiles.add(Tile.STARTING);
            tiles.remove(Tile.STARTING);
        }

        Iterator<Tile> iterator = tiles.iterator();
        while(iterator.hasNext()){
            Tile t = iterator.next();
            if (t == tile) {
                grabTiles.add(t);
            } else {
                tableTiles.add(t);
            }
            iterator.remove();
        }

        List<List<Tile>> returnTiles = Arrays.asList(grabTiles, tableTiles);

        return returnTiles;
    }
}
