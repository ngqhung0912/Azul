package pppp.group14project.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Floor {
  @Getter
  private ObservableList<Tile> tiles;


  @Getter
  private int floorScore;

  private final List<Integer> tilePoints = new ArrayList<>() {{
    add(-1);
    add(-1);
    add(-2);
    add(-2);
    add(-2);
    add(-3);
    add(-3);
  }};

  public Floor() {
    this.floorScore = 0;
    this.tiles = FXCollections.observableArrayList();
  }

  public void addTile(Tile tile) throws FullException {
    // if the floor line is full, additional tiles are discarded to the box lid
    if (this.tiles.size() < 7) {
      this.tiles.add(tile);
      updateFloorScore(tiles.size() - 1);
    } else {
      throw new FullException("Floor is full");
    }
  }

  public void updateFloorScore(int IndexOfTilesAdded) {
    this.floorScore += tilePoints.get(IndexOfTilesAdded);
  }

  private void resetScore() {
    this.floorScore = 0;
  }


  public List<Tile> emptyFloor() {
    List<Tile> returnTiles = new ArrayList<>(tiles);
    // Remove everything from tiles using an iterator
    Iterator<Tile> iterator = tiles.iterator();
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();
    }
    this.resetScore();
    return returnTiles;
  }

}
