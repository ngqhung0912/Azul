package pppp.group14project.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable, Mediator {

  /**
   * FXML for updating views
   */
  @FXML
  private GridPane innerGridMid;

  @FXML
  private GridPane innerGridLeft;

  @FXML
  private GridPane innerGridRight;

  @FXML
  private GridPane factoriesGrid;


  @Getter
  private TableController tableController;


  @Getter
  @Setter
  private Game game;

  /**
   * References to other controllers
   */
  @Setter
  @Getter
  private List<PlayerBoardController> playerBoardControllers = new ArrayList<>();

  @Getter
  @Setter
  private List<FactoryController> factoryControllers = new ArrayList<>();

  @SneakyThrows
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    // hardcoded grid indices for player boards
    Pair<Integer, Integer>[] playerGridIndices = new Pair[4];
    playerGridIndices[0] = (new Pair<>(0,0));
    playerGridIndices[1] = (new Pair<>(0,2));
    playerGridIndices[2] = (new Pair<>(1,0));
    playerGridIndices[3] = (new Pair<>(1,2));
    int gridIndex = 0;

    game = Game.getInstance();
    List<Board> boardList = game.getBoardList();

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
        playerBoardControllers.add(playerBoardController);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    try {
      Integer factoryAmount = Game.getInstance().getFactoryList().size();
      for(Integer factoryNr = 0; factoryNr < factoryAmount; factoryNr++) {
        FXMLLoader factoryLoader = new FXMLLoader((getClass().getResource("/factory-view.fxml")));
        GridPane factory = factoryLoader.load();
        FactoryController controller = factoryLoader.getController();
        factoryControllers.add(controller);
        factoriesGrid.add(factory, factoryNr%2, factoryNr/2);
      }
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
  private void postInitialize() throws FullException {
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

    for (int i = 0; i < factoryControllers.size(); i++) {
      FactoryController controller = factoryControllers.get(i);
      controller.setGameBoardController(this);
      controller.setFactory(game.getFactoryList().get(i));
      // Delegates call to child
      controller.postInitialize();
    }

    // Fill the factories
    int numberOfTilesForFactories = game.getFactoryList().size() * 4;
    List<Tile> tilesForFactories = game.getTilecontainer().grabBagTiles(numberOfTilesForFactories);
    game.fillFactories(tilesForFactories);

    game.generateTurns(0); // Start from Player 0

  }

  private void finishRound() {

    // Update starting player
    game.generateTurns(getStartingPlayer());

    // Move Tiles from Pattern to Wall, and empty Floor for each player

    for (PlayerBoardController p: playerBoardControllers) {

      List<Tile> returnTilesWall = p.moveTileFromPatternToWall();

      moveTilesToTileContainer(returnTilesWall);

      p.updateScore();
      List<Tile> returnTilesFloor = p.removeTilesFromFloor();

      returnTilesFloor.remove(Tile.STARTING);

      moveTilesToTileContainer(returnTilesFloor);
    }

    // check if the game has ended
    if(endConditionMet()) {
      endGame();
    } else {
      refillFactories();
    }
  }

  /**
   * Checks if a player has a full row, which is the end-condition of Azul
   *
   * @return whether the game should end
   */
  public boolean endConditionMet() {
    for(Board board: game.getBoardList()) {
      Wall wall = board.getWall();
      if(wall.getFullRows() > 0) {
        return true;
      }
    }
    return tileContainerEmpty();
  }

  private boolean tileContainerEmpty() {
    return game.getTilecontainer().getBagTiles().isEmpty() && game.getTilecontainer().getDiscardedTiles().isEmpty();
  }

  /**
   * Manages the end of the game
   */
  private void endGame() {
    // calculate the final scores
    PlayerBoardController winner = playerBoardControllers.get(0);
    for(PlayerBoardController player : playerBoardControllers) {
      Board board = player.getBoard();
      Wall wall = board.getWall();
      wall.updateScoreAtEndGame();
      int playerscore = wall.getWallScore();
      winner = (playerscore > winner.getBoard().getWall().getWallScore()) ? player : winner;
    }
    displayWinner(winner);
    Platform.runLater(() -> {
      String winnername = game.winner.getPlayer().getName();
      alertGameEnd(winnername);
    });
  }

  /**
   * Adds all necessary styling to show who won the game.
   *
   * @param winner The PlayerBoardController associated to the winner of the game
   */
  private void displayWinner(PlayerBoardController winner) {
    game.setWinner(winner.getBoard());
    winner.playerName.getStyleClass().add("winnerText");
    winner.boardBackground.getStyleClass().clear();
    winner.boardBackground.getStyleClass().add("winnerBoard");
    winner.getScoreController().scoreText.getStyleClass().add("winnerText");
  }

  /**
   * Creates an alert to notify the game's players that the game has ended.
   *
   * @param winnername The name of the winner of the game, can be null
   */
  private void alertGameEnd(String winnername) {
      Alert a = new Alert(Alert.AlertType.INFORMATION);
      winnername = (winnername == null || winnername == "") ? "Anonymous" : winnername;
      a.setContentText("The game has ended, and " + winnername + " has won! \n Restart the game to play again.");
      a.show();

  }

  private int getStartingPlayer() {
    int startingPlayerID = 0;
    for (int i = 0; i < playerBoardControllers.size(); i++) {
      if (playerBoardControllers.get(i).getFloorController().getFloor().getTiles().contains(Tile.STARTING)) {
        startingPlayerID = i;
        break;
      }
    }
    return startingPlayerID;
  }


  private void refillFactories() {
    List<Tile> tileToRefill = game.getTilecontainer().grabBagTiles(4 * game.getFactoryList().size());
    game.fillFactories(tileToRefill);
    try {
      tableController.getTable().addTile(Tile.STARTING);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  public void deselectAllFactories() {
    for (FactoryController f: factoryControllers) {
      f.deselectAllTiles();
    }
    tableController.unhighlightAllTiles();
  }

  public boolean factoriesAndTableEmpty() {
    for (FactoryController f: factoryControllers) {
      if (!f.getFactory().isEmpty())
        return false;
    }
    if (!tableController.getTable().isEmpty())
      return false;
    return true;
  }

  // Pass
  public void highlightCurrentPlayerBoard(Tile tileColor, Factory f) {

    int playerID = game.getNextPlayerID();

    PlayerBoardController activePlayer = playerBoardControllers.get(playerID);

    try {
      activePlayer.activate(tileColor, f);
    } catch (InvalidPositionException e) {
      throw new RuntimeException(e);
    }

  }

  public void finishPlayerTurn() {
    // If Factories are empty start next round, otherwise go to next player
    if (factoriesAndTableEmpty()) {

      finishRound();

    } else {

      game.nextPlayer();

    }

  }

  /**
   * Concrete Mediator implementation of moving tiles between different GameBoard components
   */

  @Override
  public List<Tile> removeTilesFromFloor() {
    return null;
  }

  @Override
  public void moveTileToWall(Tile tile, int rowIndex) {
  }

  @Override
  public void moveTilesToFloor(List<Tile> tiles) {
  }

  @Override
  public void moveTilesToTable(List<Tile> tiles){
    try {
      tableController.addTilesToTable(tiles);
    } catch (FullException ignore){
    }
  }

  @Override
  public void moveTileToTileContainer(Tile tile) {
    game.getTilecontainer().addDiscardedTiles(Collections.singletonList(tile));
  }

  @Override
  public void moveTilesToTileContainer(List<Tile> tile) {
    game.getTilecontainer().addDiscardedTiles(tile);
  }

  @Override
  public void updateScore() {

  }
}
