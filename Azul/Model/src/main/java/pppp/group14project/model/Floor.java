package pppp.group14project.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import java.util.Arrays;

public class Floor {
  @Getter
  private ObservableList<Tile> tiles;


  @Getter
  private int floorScore;

  private final int[] tilePoints = {-1, -1, -2,-2,-2, -3, -3};

  public Floor() {
    this.floorScore = 0;
    this.tiles = FXCollections.observableArrayList();
  }

  public void addTile(Tile tile) {
    // if the floor line is full, additional tiles are discarded to the box lid
    if (this.tiles.size() < 7) {
      this.tiles.add(tile);
    } else {
      // TODO: call box lid method to add tiles
    }
    updateFloorScore();
  }

  public void updateFloorScore() {
    int numberOfTiles = tiles.size();
    floorScore += Arrays.stream(tilePoints).limit(numberOfTiles).sum();
  }

  public void emptyFloor() { this.tiles.clear(); }
}
