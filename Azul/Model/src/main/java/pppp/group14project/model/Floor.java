package pppp.group14project.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import java.util.Arrays;

public class Floor {
  @Getter
  private ObservableList<Tile> tiles;

  private final int[] tilePoints = {-1, -1, -2,-2,-2, -3, -3};

  public Floor() {
    this.tiles = FXCollections.observableArrayList();
  }

  public void addTile(Tile tile) {
    // if the floor line is full, additional tiles are discarded to the box lid
    if (this.tiles.size() < 7) {
      this.tiles.add(tile);
    } else {
      // TODO: call box lid method to add tiles
    }
  }

  public int getScore() {
    int numberOfTiles = tiles.size();
    int score = Arrays.stream(tilePoints).limit(numberOfTiles).sum();
    return score;
  }

  public void emptyFloor() { this.tiles.clear(); }
}
