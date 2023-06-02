package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableTest {

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

        assertEquals(table.size(), 1);
        List<Tile> t = table.grabTiles(Tile.STARTING);
        assertEquals(t.get(0), Tile.STARTING);
        assertEquals(t.size(), 1);

    }

    /**
     * Test adding a lot of tiles to the Table
     */
    @Test
    void TestAddingManyTiles() throws Exception {

        List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        table.addTiles(tilesToAdd);
        assertEquals(table.size(), 9);
        assertEquals(table.getMaxNumberOfTiles(), Integer.MAX_VALUE);

    }

}
