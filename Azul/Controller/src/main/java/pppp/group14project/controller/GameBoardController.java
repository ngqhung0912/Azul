package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Pair;
import pppp.group14project.model.Game;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
  @FXML
  private GridPane innerGridMid;

  @FXML
  private GridPane innerGridLeft;

  @FXML
  private GridPane innerGridRight;

  @FXML
  private GridPane factoriesGrid;

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
        int row = playerGridIndices[gridIndex].getValue();
        int column = playerGridIndices[gridIndex].getKey();
        if(row == 0) {
          innerGridLeft.add(playerBoard, 0, column);
        } else {
          innerGridRight.add(playerBoard, 0, column);
        }
        gridIndex++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    try {
      for(Integer factoryNr = 0; factoryNr <= playerList.size()*2; factoryNr++) {
        FXMLLoader factoryLoader = new FXMLLoader((getClass().getResource("/factory-view.fxml")));
        GridPane factory = factoryLoader.load();
        factoriesGrid.add(factory, factoryNr%2, factoryNr/2);
      }

      // Also add the table at 0,1
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
