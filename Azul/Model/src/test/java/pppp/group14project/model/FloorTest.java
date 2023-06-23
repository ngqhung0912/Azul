package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    private Floor floor;

    @BeforeEach
    void setUp() {
        this.floor = new Floor();
    }

    @Test
    void addSpecificTile() {
        try {
            floor.addTile(Tile.STARTING);
        } catch (FullException e) {
            fail();
        }
        assertEquals(1, floor.getTiles().size());
        assertEquals(Tile.STARTING, floor.getTiles().get(0));
    }

    @Test
    void emptyFloorLine() {
        try {
            floor.addTile(Tile.RED);
            floor.addTile(Tile.RED);
            floor.addTile(Tile.ORANGE);

        } catch (FullException e) {
            fail();
        }
        assertEquals(3, floor.getTiles().size());
        floor.emptyFloor();
        assertEquals(0, floor.getTiles().size());
    }

    @Test
    void addMoreTileThanMaximumFloorSize() {
        try {
            floor.addTile(Tile.RED);
            floor.addTile(Tile.RED);
            floor.addTile(Tile.ORANGE);
            floor.addTile(Tile.BLACK);
            floor.addTile(Tile.WHITE);
            floor.addTile(Tile.BLUE);
            floor.addTile(Tile.WHITE);
        } catch (FullException e) {
            fail();
        }
        assertEquals(7, floor.getTiles().size());
        try {
            floor.addTile(Tile.RED);
            fail();
        } catch (FullException ignored) {
        }
        assertEquals(7, floor.getTiles().size());
    }

    @Test
    void whenFloorIsEmptyScoreIsZero() {
        assertEquals(0, floor.getFloorScore());
    }

    @Test
    void whenFloorHasOneTileScoreIsMinusOne() {
        try {
            floor.addTile(Tile.RED);
        } catch (FullException e) {
            fail();
        }
        assertEquals(-1, floor.getFloorScore());
    }

    @Test
    void whenFloorIsFullScoreIsMinusFourteen() {
        try {
            floor.addTile(Tile.STARTING);
            floor.addTile(Tile.RED);
            floor.addTile(Tile.RED);
            floor.addTile(Tile.ORANGE);
            floor.addTile(Tile.BLACK);
            floor.addTile(Tile.WHITE);
            floor.addTile(Tile.BLUE);
        } catch (FullException e) {
            fail();
        }
        assertEquals(-14, floor.getFloorScore());
    }
}