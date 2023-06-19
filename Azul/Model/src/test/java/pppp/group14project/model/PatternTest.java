package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatternTest {

    private Pattern pattern;

    @BeforeEach
    void setUp() throws Exception {
        pattern = new Pattern();
    }

    /**
     * Test adding tiles to the PatternLine
     */
    @Test
    void TestAddingTiles() throws WrongTileException {

        List<Tile> tilesToAdd = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);

        List<Tile> returnedTiles0 = pattern.addTiles(0, tilesToAdd);
        List<Tile> returnedTiles1 = pattern.addTiles(1, tilesToAdd);
        List<Tile> returnedTiles2 = pattern.addTiles(2, tilesToAdd);
        List<Tile> returnedTiles3 = pattern.addTiles(3, tilesToAdd);
        List<Tile> returnedTiles4 = pattern.addTiles(4, tilesToAdd);

        assertEquals(returnedTiles0.size(), 4);
        assertEquals(returnedTiles1.size(), 3);
        assertEquals(returnedTiles2.size(), 2);
        assertEquals(returnedTiles3.size(), 1);
        assertEquals(returnedTiles4.size(), 0);

    }
    @Test
    void testAddAndRemoveCorrectTilesToPattern() {
        List<Tile> tileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            tileList.add(Tile.BLUE);

            try {
                pattern.addTiles(i, tileList);
            } catch (WrongTileException e) {
                fail("Should not throw WrongTileException");
            }
            assertEquals(tileList.size(), pattern.getPatternLines().get(i).numberOfFullSpaces());
            assertEquals(0, pattern.getPatternLines().get(i).numberOfFreeSpaces());
            assertTrue(pattern.getPatternLines().get(i).isFull());
            assertFalse(pattern.getPatternLines().get(i).isEmpty());

            pattern.getPatternLines().get(i).empty();

            assertFalse(pattern.getPatternLines().get(i).isFull());
            assertTrue(pattern.getPatternLines().get(i).isEmpty());

            assertEquals(tileList.size(), pattern.getPatternLines().get(i).numberOfFreeSpaces());
            assertEquals(0, pattern.getPatternLines().get(i).numberOfFullSpaces());

        }
    }

    @Test
    void testAddDifferentColorToSamePattern() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        try {
            pattern.addTiles(1, tileList);
            fail("Expect WrongTileException");
        } catch (WrongTileException ignored) {}
    }


}
