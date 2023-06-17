package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Wall wall;

    @BeforeEach
    void setUp() {
        this.wall = new Wall();
    }

    @Test
    void addSpecificTile() {
        try {
            wall.addTile(Tile.RED, 3);
        } catch (FullException e) {
            fail();
        }
        assertEquals(1, wall.countNonNullElementsInRow(wall.getRow(3)));
        assertTrue(wall.isTileInRow(Tile.RED, 3));

    }

    @Test
    void incorrectTileLocation() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            try {
                wall.addTile(Tile.RED, 5);
            } catch (FullException e) {
                fail();
            }
        });
    }


    @Test
    void rowIsFullCorrectColors() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
            wall.addTile(Tile.ORANGE, 0);
        } catch (FullException e) {
            fail();
        }

        assertTrue(wall.isRowFull(wall.getRow(0)));
        assertEquals(5, wall.getRow(0).size());
    }

    @Test
    void AddSameTilesToOneRow() {

        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
        } catch (FullException e) {
            fail();
        }
        try {
            wall.addTile(Tile.BLACK, 0);
            fail();

        } catch (FullException ignored) {}

        try {
            wall.addTile(Tile.RED, 0);
            fail();

        } catch (FullException ignored) {}

        assertNotEquals(5, wall.countNonNullElementsInRow(wall.getRow(0)));
    }

    @Test
    void rowIsNotFull() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
        } catch (FullException e) {
            fail();
        }
        assertFalse(wall.isRowFull(wall.getRow(0)));
    }

    @Test
    void emptyWall() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
        } catch (FullException e) {
            fail();
        }
        assertEquals(4, wall.getTilesInWall().size());
        wall.emptyWall();
        assertEquals(0, wall.getTilesInWall().size());
    }


    @Test
    void scoreOfOneTile() {
        try {
            wall.addTile(Tile.BLACK, 0);
        } catch (FullException e) {
            fail();
        }
        assertEquals(1, wall.getWallScore());
    }

    @Test
    void scoreTwoNotConnectedTiles() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
        } catch (FullException e) {
            fail();
        }

        assertEquals(2, wall.getWallScore());
    }

    @Test
    void scoreTwoConnectedTiles() {
        try {
            wall.addTile(Tile.ORANGE, 3);
            wall.addTile(Tile.BLUE, 4);
        } catch (FullException e) {
            fail();
        }
        assertEquals(3, wall.getWallScore());
    }

    @Test
    void getFullColumnsandRows() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
            wall.addTile(Tile.ORANGE, 0);
        } catch (FullException e) {
            fail();
        }
        assertEquals(1, wall.getFullRows());

        try {
            wall.addTile(Tile.RED, 1);
            wall.addTile(Tile.ORANGE, 2);
            wall.addTile(Tile.BLUE, 3);
            wall.addTile(Tile.WHITE, 4);
        } catch (FullException e) {
            fail();
        }
        assertEquals(1, wall.getFullCols());
    }


}
