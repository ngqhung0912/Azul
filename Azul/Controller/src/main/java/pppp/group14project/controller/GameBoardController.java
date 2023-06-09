package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Pair;
import pppp.group14project.model.Board;
import pppp.group14project.model.Game;

import java.io.IOException;
import java.net.URL;
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

    List<Board> boardList = Game.getInstance().getBoardList();

    for (Board board : boardList) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/player-board-view.fxml"));
        GridPane playerBoardView = loader.load();
        PlayerBoardController playerBoardController = loader.getController();
        playerBoardController.setPlayerName(board.getPlayer().getName());
        gameBoardGrid.add(playerBoardView, playerGridIndices[gridIndex].getValue(), playerGridIndices[gridIndex].getKey());
        gridIndex++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


  }
}
