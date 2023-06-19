package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    private Factory factory;

    @BeforeEach
    void setUp() {
        factory = new Factory();
    }

    /**
     * Test initializing and adding a list of tiles to the Factory
     */
    @Test
    void TestCreatingFactory() throws FullException {

        assertDoesNotThrow(() -> {
            List<Tile> t = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
            Factory f = new Factory();
            f.addTiles(t);
        });

        FullException thrown = assertThrows(
                FullException.class,
                () -> {
                    List<Tile> t2 = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
                    Factory f2 = new Factory();
                    f2.addTiles(t2);
                }
        );

    }


    /**
     * Test initializing and adding a list of tiles to the Factory
     */
    @Test
    void TestTileAddingToFactory() throws Exception {

        assertEquals(factory.size(), 0);

        List<Tile> tilesToAdd = new ArrayList<>();
        tilesToAdd.add(Tile.BLUE);
        tilesToAdd.add(Tile.BLUE);
        tilesToAdd.add(Tile.RED);
        tilesToAdd.add(Tile.ORANGE);
        factory.addTiles(tilesToAdd);

        assertEquals(factory.size(), 4);

    }

    /**
     * Test initializing and adding a list of tiles to the Factory
     */
    @Test
    void TestTileGrabbingFromFactory() throws Exception {

        List<Tile> tilesToAdd = new ArrayList<>();
        tilesToAdd.add(Tile.BLUE);
        tilesToAdd.add(Tile.BLUE);
        tilesToAdd.add(Tile.RED);
        tilesToAdd.add(Tile.ORANGE);
        factory.addTiles(tilesToAdd);

        // Two BLUE tiles returned
        factory.updateTile(Tile.BLUE);
        assertEquals(Tile.BLUE.toString(), factory.getSelected_colour().getValue());
        assertEquals(2, factory.countColour(Tile.BLUE));

        // No tiles returned
        factory.updateTile(Tile.WHITE);

        assertEquals(Tile.BLUE.toString(), factory.getSelected_colour().getValue());
        assertEquals(2, factory.countColour(Tile.BLUE));

    }

}
