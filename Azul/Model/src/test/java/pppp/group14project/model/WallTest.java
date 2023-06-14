package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Wall wall;

    @BeforeEach
    void setUp() {
        this.wall = new Wall();
    }

    @Test
    void addSpecificTile() {
        wall.addTile(Tile.RED, 3, 0);
        assertEquals(1, wall.countNonNullElementsInRow(wall.getRow(3)));
        assertEquals(Tile.RED, wall.getRow(3).get(0));

    }

    @Test
    void incorrectTileLocation() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            wall.addTile(Tile.RED, 5, 2);
        });
    }

    @Test
    void tileIsAlreadyInRow() {
        wall.addTile(Tile.RED, 3, 3);
        assertTrue(wall.isTileInRow(Tile.RED, 3));
    }

    @Test
    void rowIsFullCorrectColors() {
        wall.addTile(Tile.BLACK, 0, 0);
        wall.addTile(Tile.BLUE, 0, 1);
        wall.addTile(Tile.WHITE, 0, 2);
        wall.addTile(Tile.RED, 0, 3);
        wall.addTile(Tile.ORANGE, 0, 4);
        assertTrue(wall.isRowFull(wall.getRow(0)));
        assertEquals(5, wall.getRow(0).size());
    }

    @Test
    void rowIsFullInorrectColors() {
        wall.addTile(Tile.BLACK, 0, 0);
        wall.addTile(Tile.BLACK, 0, 1);
        wall.addTile(Tile.WHITE, 0, 2);
        wall.addTile(Tile.RED, 0, 3);
        wall.addTile(Tile.RED, 0, 4);
        assertNotEquals(5, wall.countNonNullElementsInRow(wall.getRow(0)));
    }

    @Test
    void rowIsNotFull() {
        wall.addTile(Tile.RED, 0, 0);
        wall.addTile(Tile.RED, 0, 1);
        wall.addTile(Tile.RED, 0, 2);
        wall.addTile(Tile.RED, 0, 3);
        assertFalse(wall.isRowFull(wall.getRow(0)));
    }

    @Test
    void emptyWall() {
        wall.addTile(Tile.RED, 0, 0);
        wall.addTile(Tile.RED, 3, 1);
        wall.addTile(Tile.RED, 4, 2);
        assertEquals(3, wall.getTilesInWall().size());
        wall.emptyWall();
        assertEquals(0, wall.getTilesInWall().size());
    }


    @Test
    void scoreOfOneTile() {
        wall.addTile(Tile.RED, 3, 2);
        assertEquals(1, wall.getWallScore());
    }

    @Test
    void scoreTwoNotConnectedTiles() {
        wall.addTile(Tile.RED, 3, 2);
        wall.addTile(Tile.BLUE, 4, 3);
        assertEquals(2, wall.getWallScore());
    }

    @Test
    void scoreTwoConnectedTiles() {
        wall.addTile(Tile.RED, 3, 2);
        wall.addTile(Tile.BLUE, 4, 2);
        assertEquals(3, wall.getWallScore());
    }

    @Test
    void getFullColumnsandRows() {
        wall.addTile(Tile.BLACK, 0, 1);
        wall.addTile(Tile.BLUE, 1, 1);
        wall.addTile(Tile.WHITE, 2, 1);
        wall.addTile(Tile.RED, 3, 1);
        wall.addTile(Tile.ORANGE, 4, 1);
        assertEquals(1, wall.getFullCols());
        wall.addTile(Tile.BLUE, 0, 0);
        wall.addTile(Tile.WHITE, 0, 2);
        wall.addTile(Tile.RED, 0, 3);
        wall.addTile(Tile.ORANGE, 0, 4);
        assertEquals(1, wall.getFullRows());
    }


}
