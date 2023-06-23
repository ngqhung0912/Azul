package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableTest {

    private Table table;

    @BeforeEach
    void setUp() throws Exception {
        table = new Table();
    }

    /**
     * Test initializing and adding a list of tiles to the Factory
     */
    @Test
    void TestInitializingWithStartingTile() throws Exception {
        assertEquals(1, table.size());
        assertEquals(true, table.starting_tile);
    }

    /**
     * Test adding a lot of tiles to the Table
     */
    @Test
    void TestAddingManyTiles() throws Exception {

        List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        table.addTiles(tilesToAdd);
        assertEquals(9, table.size());
        assertEquals(Integer.MAX_VALUE, table.getMaxNumberOfTiles());

    }

}
