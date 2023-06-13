package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Floor;

import pppp.group14project.model.Tile;

import java.util.List;

public class FloorController {

  /**
   * FXML for updating views
   */
  @FXML
  private GridPane floorGridPane;

  /**
   * Floor data model
   */
  @Getter
  @Setter
  Floor floor;

  @Setter
  @Getter
  private PlayerBoardController playerBoardController;

  /**
   * References to other controllers
   */
  private final Color emptyColor = Color.WHITE;

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

  /**
   * Initializes the models, once all models of its parent models have loaded
   */
  public void postInitialize() {

    // Register event handlers here::

  }

}
