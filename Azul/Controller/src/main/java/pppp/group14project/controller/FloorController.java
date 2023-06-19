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
import pppp.group14project.model.Floor;
import pppp.group14project.model.Tile;

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
      floor.addTile(tile);
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

    while (tileNodeIterator.hasNext() && tileIterator.hasNext()) {
      Node tileNode = tileNodeIterator.next();
      Tile tile = tileIterator.next();

      tileNode.getStyleClass().clear();
      tileNode.getStyleClass().add(String.valueOf(tile));
      if (tile == Tile.STARTING){
        Button starting = (Button) tileNode;
        starting.setText("1");
        starting.setAlignment(Pos.CENTER);
      }


    }
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
