package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Floor;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

  public void addTilesToFloor(List<Tile> tiles) {
    for (Tile tile : tiles) {
      try {
        floor.addTile(tile);
      } catch (FullException e) {
        playerBoardController.moveTileToTileContainer(tile);
      }
    }
  }

  /**
   * Get all button nodes in the gridPane and set their colours according to the tiles present in the observable list
   * @param tiles
   */
  public void setTileColours(ObservableList<Tile> tiles) {

    ObservableList<Node> tileNodes = floorGridPane.getChildren().filtered(node -> node instanceof Button);

    Iterator<Node> tileNodeIterator = tileNodes.iterator();
    Iterator<Tile> tileIterator = tiles.iterator();

    while (tileNodeIterator.hasNext()) {
      Node tileNode = tileNodeIterator.next();

      if (tileIterator.hasNext()) {
        Tile tile = tileIterator.next();

        tileNode.getStyleClass().clear();
        tileNode.getStyleClass().add("is-colored");
        tileNode.getStyleClass().add(String.valueOf(tile));
        if (tile == Tile.STARTING) {
          Button starting = (Button) tileNode;
          starting.setText("1");
          starting.setAlignment(Pos.CENTER);
        }
      } else {

        // Resets the Tile
        tileNode.getStyleClass().clear();
        Button b = (Button) tileNode;
        b.setText("");
        tileNode.getStyleClass().add("button");
        tileNode.getStyleClass().add("floorTileEmpty");
      }

    }
  }

  public void highlightFloor(Tile tile, Factory factory) {
    // check if we have enough space
    Integer num_current_tiles = floor.getTiles().size();
    Integer num_tiles_to_place = Collections.frequency(factory.getTiles(),tile);
    if(num_current_tiles + num_tiles_to_place > 7) {
      return;
    }
    Button first_empty_tile = (Button) floorGridPane.getChildren().get(num_current_tiles+7);
    first_empty_tile.getStyleClass().add("tile-option");
    first_empty_tile.setOnAction(e -> {moveTilesToFloorFromFactory(tile, factory, first_empty_tile); });
  }

  public void moveTilesToFloorFromFactory(Tile tile, Factory factory, Button clickedSpace) {
    List<List<Tile>> returnTiles = factory.grabTiles(tile);
    List<Tile> grabbedTiles = returnTiles.get(0);
    List<Tile> tableTiles = returnTiles.get(1);

    playerBoardController.getGameBoardController().moveTilesToTable(tableTiles);

    addTilesToFloor(grabbedTiles);
    playerBoardController.getGameBoardController().finishPlayerTurn();
    clickedSpace.getStyleClass().remove("tile-option");
  }


  /**
   * Initializes the models, once all models of its parent models have loaded
   */
  public void postInitialize() {
    floor.getTiles().addListener((ListChangeListener<Tile>) change -> {
      setTileColours(floor.getTiles());
    });
  }
}
