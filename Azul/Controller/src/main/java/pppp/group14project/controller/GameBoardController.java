package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Board;
import pppp.group14project.model.Game;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {

  /**
   * FXML for updating views
   */
  @FXML
  private GridPane innerGridMid;

  @FXML
  private GridPane innerGridLeft;

  @FXML
  private GridPane innerGridRight;

  @Getter
  private TableController tableController;

  @Getter
  private FactoriesController factoriesController;

  @Getter
  @Setter
  private Game game;

  /**
   * References to other controllers
   */
  @Setter
  @Getter
  private List<PlayerBoardController> playerBoardControllers = new ArrayList<>();

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

        int row = playerGridIndices[gridIndex].getValue();
        int column = playerGridIndices[gridIndex].getKey();
        if(row == 0) {
          innerGridLeft.add(playerBoardView, 0, column);
        } else {
          innerGridRight.add(playerBoardView, 0, column);
        }
        gridIndex++;

        // Adds the playerBoardControllers
        this.playerBoardControllers.add(playerBoardController);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    try {
      FXMLLoader factoryLoader = new FXMLLoader((getClass().getResource("/factories-view.fxml")));
      GridPane factories = factoryLoader.load();
      factoriesController = factoryLoader.getController();
      factoriesController.setNumberOfPlayers(boardList.size());
      innerGridMid.add(factories, 0, 0);

      // Also add the table at 0,1
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      FXMLLoader tableLoader = new FXMLLoader((getClass().getResource("/game-table-view.fxml")));
      GridPane table = tableLoader.load();
      tableController = tableLoader.getController();
      innerGridMid.add(table, 1, 0);

      // Also add the table at 0,1
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Initialize models at the end of Game initialization
    postInitialize();
  }

  /**
   * Initializes the models, once all models of its parent models have loaded
   */
  private void postInitialize() {
    game = Game.getInstance();
    List<Board> boards = game.getBoardList();
    for (int i = 0; i < playerBoardControllers.size(); i++) {
      PlayerBoardController p = playerBoardControllers.get(i);
      p.setGameBoardController(this);
      p.setBoard(boards.get(i));
      // Delegates call to child
      p.postInitialize();
    }
    tableController.setGameBoardController(this);
    tableController.setTable(game.getTable());
    tableController.postInitialize();
    factoriesController.setGameBoardController(this);
    factoriesController.setFactory(game.getFactory());
    factoriesController.postInitialize();
  }
}
