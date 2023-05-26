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

  public void addTile(Tile tile) {}

  public void emptyFloor() {}
}
