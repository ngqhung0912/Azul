package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

  private Floor floor;

  @BeforeEach
  void setUp() {
    this.floor = new Floor();
  }

  @Test
  void addSpecificTile(){
    floor.addTile(Tile.STARTING);
    assertEquals(1, floor.getTiles().size());
    assertEquals(Tile.STARTING, floor.getTiles().get(0));
  }

  @Test
  void emptyFloorLine(){
    floor.addTile(Tile.RED);
    floor.addTile(Tile.RED);
    floor.addTile(Tile.ORANGE);
    assertEquals(3, floor.getTiles().size());
    floor.emptyFloor();
    assertEquals(0, floor.getTiles().size());
  }

  @Test
  void addMoreTileThanMaximumFloorSize() {
    floor.addTile(Tile.RED);
    floor.addTile(Tile.RED);
    floor.addTile(Tile.ORANGE);
    floor.addTile(Tile.BLACK);
    floor.addTile(Tile.WHITE);
    floor.addTile(Tile.BLUE);
    floor.addTile(Tile.WHITE);
    assertEquals(7, floor.getTiles().size());
    floor.addTile(Tile.RED);
    assertEquals(7, floor.getTiles().size());
  }
}