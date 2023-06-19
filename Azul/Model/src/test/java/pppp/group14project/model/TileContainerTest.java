package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Tile;
import pppp.group14project.model.TileContainer;
import pppp.group14project.model.exceptions.EmptyException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void TestTileGrabbing() throws EmptyException {

        List<Tile> result = tileContainer.grabBagTiles(10);
        assertEquals(tileContainer.getBagTiles().size(), 90);
        assertEquals(result.size(), 10);

    }

    /**
     * Test grabbing too many tiles from the bag in the TileContainer
     */
    @Test
    void TestTileGrabbingTooMany() {

        assertDoesNotThrow(() -> {
            List<Tile> t = tileContainer.grabBagTiles(90);
            // Add tiles to discarded Tiles
            tileContainer.addDiscardedTiles(t);
            // Test if it automatically moves discarded Tiles back
            tileContainer.grabBagTiles(90);
        });

    }

}