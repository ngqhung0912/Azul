package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileContainer {

    private static final int DEFAULT_NUMBER_OF_TILES_PER_COLOR = 20;
    private static TileContainer tileContainer = new TileContainer();

    public static TileContainer getTileContainer() {
        return TileContainer.getTileContainer();
    }

    @Getter
    @Setter
    private List<Tile> bagTiles;

    @Getter
    @Setter
    private List<Tile> discardedTiles;

    public TileContainer() {
        reset();
    }

    public void reset() {
        this.bagTiles = new ArrayList<>();
        this.discardedTiles = new ArrayList<>();
        for (int i = 0; i < DEFAULT_NUMBER_OF_TILES_PER_COLOR; i++ ) {
            this.bagTiles.add(Tile.BLACK);
            this.bagTiles.add(Tile.BLUE);
            this.bagTiles.add(Tile.WHITE);
            this.bagTiles.add(Tile.ORANGE);
            this.bagTiles.add(Tile.RED);
        }
    }

    /** Gets a number of tiles from the bag
     *
     * @param numberOfTiles the number of tiles to get from the bag
     * @return a list of the tiles
     */
    public List<Tile> grabBagTiles(int numberOfTiles) {
        List<Tile> returnedTiles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numberOfTiles; i++) {
            try {
                Tile t = this.bagTiles.remove(rand.nextInt(this.bagTiles.size()));
                returnedTiles.add(t);
            } catch(IndexOutOfBoundsException | IllegalArgumentException e) {
                this.bagTiles = returnedTiles;
                return null;
            }
        }
        return returnedTiles;
    }

    public void addDiscardedTiles(List<Tile> discardedTilesToAdd) {
        discardedTiles.addAll(discardedTilesToAdd);
    }

    public void moveDiscardedTiles() {
        bagTiles.addAll(discardedTiles);
        discardedTiles = new ArrayList<>();
    }

    public static void main(String[] args) {
        TileContainer tileContainer = new TileContainer();
        System.out.println(tileContainer.grabBagTiles(66));
        System.out.println(tileContainer.getBagTiles().size());
    }

}

