package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Floor;
import pppp.group14project.model.Game;
import pppp.group14project.model.Tile;

import java.util.List;
import java.io.IOException;

public class FloorController {

  /**
   * Floor data model
   */
  @Getter
  @Setter
  Floor floor;

  @FXML
  private GridPane floorGridPane;

  private final javafx.scene.paint.Color emptyColor = javafx.scene.paint.Color.WHITE;


  public Rectangle findLeftmostEmptyTile(){

    double minX = Double.MAX_VALUE;
    Rectangle leftmostEmptyTile = null;

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Rectangle) {
        Rectangle rectangle = (Rectangle) node;
        Bounds bounds = rectangle.getBoundsInParent();
        if (bounds.getMinX() < minX && rectangle.getFill().equals(emptyColor)) {
          minX = bounds.getMinX();
          leftmostEmptyTile = rectangle;
        }
      }
    }

    return leftmostEmptyTile;
  }

  public void addTilesToFloor(List<Tile> tiles) {

    for (Tile tile : tiles) {
      Rectangle leftmostEmptyTile = findLeftmostEmptyTile();
      if (leftmostEmptyTile != null) {
        leftmostEmptyTile.setFill(javafx.scene.paint.Color.GREEN); // TODO: how to get color info from tile?
      }
      floor.addTile(tile);
    }
  }

  // Button exists only for testing purposes
  public void onFloorButtonClick() {
    addTilesToFloor(List.of(Tile.RED, Tile.ORANGE));
  }
}
