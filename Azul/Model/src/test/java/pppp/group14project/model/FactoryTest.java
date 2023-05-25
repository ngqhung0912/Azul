package UnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Tile;
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
            Factory f = new Factory(t);
        });

        FullException thrown = assertThrows(
                FullException.class,
                () -> {
                    List<Tile> t2 = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
                    Factory f2 = new Factory(t2);
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
        List<Tile> grabbedTiles = factory.grabTiles(Tile.BLUE);
        List<Tile> expectedList = Arrays.asList(Tile.BLUE, Tile.BLUE);

        assertEquals(grabbedTiles, expectedList);
        assertEquals(factory.size(), 2);

        // No tiles returned
        List<Tile> grabbedTiles2 = factory.grabTiles(Tile.BLUE);
        List<Tile> expectedList2 = Arrays.asList();

        assertEquals(grabbedTiles, expectedList);
        assertEquals(factory.size(), 2);

    }

}
