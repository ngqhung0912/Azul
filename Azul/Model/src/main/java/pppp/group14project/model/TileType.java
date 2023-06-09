package pppp.group14project.model;

public enum TileType  {
    BLACK(Tile.BLACK),
    BLUE(Tile.BLUE),
    AQUITE(Tile.WHITE),
    ORANGE(Tile.ORANGE),
    RED(Tile.RED),
    STARTING(Tile.STARTING);


    private final Tile tile;

    TileType(Tile tile) {
        this.tile = tile;
    }

    public Tile toTile() {
        return this.tile;
    }

}
