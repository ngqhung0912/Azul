package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileContainer {

    private static Integer DEFAULT_NUMBER_OF_TILES_PER_COLOR = 20;
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
    }

    public List<Tile> grabBagTiles(Integer numberOfTiles) {
        return new ArrayList<>();
    }

    public void addDiscardedTiles(List<Tile> discardedTilesToAdd) {
        discardedTiles.addAll(discardedTilesToAdd);
    }

    public void moveDiscardedTiles() {
    }

}

