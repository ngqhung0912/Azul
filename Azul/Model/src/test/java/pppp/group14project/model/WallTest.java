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
        assertEquals(1, wall.getRow(3).size());
        assertEquals(Tile.RED, wall.getRow(3).get(0));

    }

    @Test
    void incorrectTileLocation() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            wall.addTile(Tile.RED, 5, 2);
        });
    }

    @Test
    void rowIsFull() {
        wall.addTile(Tile.RED, 0, 0);
        wall.addTile(Tile.RED, 0, 1);
        wall.addTile(Tile.RED, 0, 2);
        wall.addTile(Tile.RED, 0, 3);
        wall.addTile(Tile.RED, 0, 4);
        assertTrue(wall.isRowFull(wall.getRow(0)));
        assertEquals(5, wall.getRow(0).size());
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


}
