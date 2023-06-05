package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Pair;
import pppp.group14project.model.Game;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
  @FXML
  private GridPane gameBoardGrid;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    // hardcoded grid indices for player boards
    Pair<Integer, Integer>[] playerGridIndices = new Pair[4];
    playerGridIndices[0] = (new Pair<>(0,0));
    playerGridIndices[1] = (new Pair<>(0,2));
    playerGridIndices[2] = (new Pair<>(1,0));
    playerGridIndices[3] = (new Pair<>(1,2));
    int gridIndex = 0;

    List<String> playerList = Game.getInstance().getPlayerNameList();

    for (String playerName : playerList) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/player-board-view.fxml"));
        GridPane playerBoard = loader.load();
        PlayerBoardController playerBoardController = loader.getController();
        playerBoardController.setPlayerName(playerName);
        gameBoardGrid.add(playerBoard, playerGridIndices[gridIndex].getValue(), playerGridIndices[gridIndex].getKey());
        gridIndex++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      FXMLLoader tableLoader = new FXMLLoader((getClass().getResource("/game-table-view.fxml")));
      GridPane table = tableLoader.load();
      gameBoardGrid.add(table, 1, 1);
      TableController tableController = new TableController();
      Table tableA = new Table();
      List<Tile> tiles = new ArrayList<>();
      tiles.add(Tile.BLUE);
      tiles.add(Tile.RED);
      tableController.addTilesToTable(tableA, tiles, table);
      table.getStylesheets().add("game-table-view-styles.css");


    } catch (IOException | FullException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
