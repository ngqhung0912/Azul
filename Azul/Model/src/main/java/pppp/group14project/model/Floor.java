package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Floor {
  @Getter
  private List<Tile> tiles;

  public Floor() {
    this.tiles = new ArrayList<>();
  }

  public void addTile(Tile tile) {
    // if the floor line is full, additional tiles are discarded to the box lid
    if (this.tiles.size() < 7) {
      this.tiles.add(tile);
    } else {
      // TODO: call box lid method to add tiles
    }
  }

  public void emptyFloor() { this.tiles.clear(); }
}
