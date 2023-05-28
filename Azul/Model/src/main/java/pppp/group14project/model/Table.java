package pppp.group14project.model;

/**
 * Differences between Table and Factory:
 * - Table is initialized with 1 STARTING Tile
 * - Table size is infinite
 */
public class Table extends Factory {

    /**
     * Used to update the default maximum number of tiles
     * @return the maximum number of tiles
     */
    @Override
    public int getMaxNumberOfTiles() {
        return Integer.MAX_VALUE;
    }

    /**
     * Adds a starting tile to the Table
     */
    public Table() throws Exception {
        this.addTile(Tile.STARTING);
    }

}
