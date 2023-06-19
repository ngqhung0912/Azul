package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    protected Wall wall;

    @BeforeEach
    void setUp() {
        this.wall = new Wall();
    }

    @Test
    void addSpecificTile() {
        try {
            wall.addTile(Tile.RED, 3);
        } catch (WrongTileException | FullException e) {
            fail();
        }
        assertEquals(1, wall.countTilesInRow(3));
    }

    @Test
    void incorrectTileLocation() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            try {
                wall.addTile(Tile.RED, 5);
            } catch (WrongTileException | FullException e) {
                fail();
            }
        });
    }


    @Test
    void AddSameTilesToOneRow() {

        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
        } catch (WrongTileException | FullException e) {
            fail();
        }
        try {
            wall.addTile(Tile.BLACK, 0);
            fail();

        } catch (WrongTileException | FullException ignored) {}

        try {
            wall.addTile(Tile.RED, 0);
            fail();

        } catch (WrongTileException | FullException ignored) {}

        assertNotEquals(5, wall.countTilesInRow(0));
    }


    @Test
    void emptyWall() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.WHITE, 0);
            wall.addTile(Tile.RED, 0);
        } catch (WrongTileException | FullException e) {
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
        } catch (WrongTileException | FullException e) {
            fail();
        }
        assertEquals(1, wall.getWallScore());
    }

    @Test
    void scoreTwoNotConnectedTiles() {
        try {
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.BLUE, 0);
        } catch (WrongTileException | FullException e) {
            fail();
        }

        assertEquals(2, wall.getWallScore());
    }

    @Test
    void testScoringSameColumnAndRow() {
        try {
            wall.addTile(Tile.ORANGE, 2);
            assertEquals(1, wall.getWallScore());

            wall.addTile(Tile.WHITE, 4);
            assertEquals(2, wall.getWallScore());

            wall.addTile(Tile.WHITE, 3);
            assertEquals(3, wall.getWallScore());

            wall.addTile(Tile.ORANGE, 3);
            assertEquals(4, wall.getWallScore());

            wall.addTile(Tile.BLACK, 3);
            assertEquals(6, wall.getWallScore());

            wall.addTile(Tile.RED, 3);
            assertEquals(9, wall.getWallScore());

            wall.addTile(Tile.RED, 1);
            assertEquals(11, wall.getWallScore());

            wall.addTile(Tile.BLACK, 0);
            assertEquals(14, wall.getWallScore());

            wall.addTile(Tile.BLUE, 3);
            assertEquals(24, wall.getWallScore());
        } catch (WrongTileException | FullException e) {
            fail();
        }
    }
    @Test
    void testScoringSameRow() {
        try {
            wall.addTile(Tile.BLUE, 2);
            assertEquals(1, wall.getWallScore());

            wall.addTile(Tile.WHITE, 2);
            assertEquals(3, wall.getWallScore());

            wall.addTile(Tile.BLACK, 2);
            assertEquals(6, wall.getWallScore());

            wall.addTile(Tile.ORANGE, 2);
            assertEquals(10, wall.getWallScore());

            wall.addTile(Tile.RED, 2);
            assertEquals(15, wall.getWallScore());

        } catch (WrongTileException | FullException e) {
            fail();
        }
    }

    @Test
    void testScoringSameColumn() {
        try {
            wall.addTile(Tile.BLUE, 2);
            assertEquals(1, wall.getWallScore());

            wall.addTile(Tile.ORANGE, 1);
            assertEquals(3, wall.getWallScore());

            wall.addTile(Tile.WHITE, 3);
            assertEquals(6, wall.getWallScore());

            wall.addTile(Tile.BLACK, 4);
            assertEquals(10, wall.getWallScore());

            wall.addTile(Tile.RED, 0);
            assertEquals(15, wall.getWallScore());

        } catch (WrongTileException | FullException e) {
            fail();
        }
    }
    @Test
    void testScoringAtEndGame() {
        try {
            // ADDING A FULL ROW
            wall.addTile(Tile.BLUE, 0);
            wall.addTile(Tile.ORANGE, 0);
            wall.addTile(Tile.RED, 0);
            wall.addTile(Tile.BLACK, 0);
            wall.addTile(Tile.WHITE, 0);
            assertEquals(15, wall.getWallScore());

            // ADDING A FULL COLUMN
            wall.addTile(Tile.RED, 1);
            wall.addTile(Tile.ORANGE, 2);
            wall.addTile(Tile.BLUE, 3);
            wall.addTile(Tile.WHITE, 4);

            assertEquals(29, wall.getWallScore());

            // ADDING A FULL COLOR
            wall.addTile(Tile.BLUE, 1);
            wall.addTile(Tile.BLUE, 2);
            wall.addTile(Tile.BLUE, 4);

        } catch (WrongTileException | FullException e) {
            fail();
        }
        wall.updateScoreAtEndGame();
        assertEquals(54, wall.getWallScore());
    }
}
