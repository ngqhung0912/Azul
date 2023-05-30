package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    private PatternLine patternLine;

    @BeforeEach
    void setUp() throws Exception {
        patternLine = new PatternLine(4);
    }

    /**
     * Test adding tiles to the PatternLine
     */
    @Test
    void TestAddingTiles() throws WrongTileException {

        assertFalse(patternLine.isFull());

        List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        patternLine.addTiles(tilesToAdd);

        assertTrue(patternLine.isFull());

    }

    /**
     * Test adding tiles to the PatternLine
     */
    @Test
    void TestAddingTooManyTiles() throws WrongTileException {

        assertFalse(patternLine.isFull());
        List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        List<Tile> returnedTiles = patternLine.addTiles(tilesToAdd);
        assertEquals(returnedTiles.size(), 2);

    }

    /**
     * Test adding tiles to the PatternLine
     */
    @Test
    void TestAddingWrongTiles() {

        assertDoesNotThrow(() -> {
            List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE);
            patternLine.addTiles(tilesToAdd);
        });

        assertThrows(
                WrongTileException.class,
                () -> {
                    List<Tile> tilesToAdd2 = Arrays.asList(Tile.RED);
                    patternLine.addTiles(tilesToAdd2);
                }
        );

        patternLine.empty();

        assertThrows(
                WrongTileException.class,
                () -> {
                    List<Tile> tilesToAdd2 = Arrays.asList(Tile.RED, Tile.BLUE);
                    patternLine.addTiles(tilesToAdd2);
                }
        );

    }

}
