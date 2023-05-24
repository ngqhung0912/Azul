package UnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Tile;
import pppp.group14project.model.TileContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TileContainerTest {

    private TileContainer tileContainer;

    @BeforeEach
    void setUp() {
        tileContainer = new TileContainer();
    }

    /**
     * Test creating a TileContainer
     */
    @Test
    void TestTileContainerCreation() {

        assertEquals(tileContainer.getBagTiles().size(), 100);

    }

    /**
     * Test grabbing tiles from the bag in the TileContainer
     */
    @Test
    void TestTileGrabbing() {

        List<Tile> result = tileContainer.grabBagTiles(10);
        assertEquals(tileContainer.getBagTiles().size(), 90);
        assertEquals(result.size(), 10);

    }

    /**
     * Test grabbing too many tiles from the bag in the TileContainer
     */
    @Test
    void TestTileGrabbingError() {

        List<Tile> result = tileContainer.grabBagTiles(110);
        assertEquals(tileContainer.getBagTiles().size(), 100);
        assertNull(result);

    }

}