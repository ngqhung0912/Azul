package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.FullException;


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

    System.out.println("All Factories and Table are empty");

    // Update starting player
    int startingPlayerID = 0;
    for (int i = 0; i < playerBoardControllers.size(); i++) {
      if (playerBoardControllers.get(i).getFloorController().getFloor().getTiles().contains(Tile.STARTING)) {
        startingPlayerID = i;
        break;
      }
    }
    game.generateTurns(startingPlayerID);
    System.out.println("Player " + startingPlayerID + " starts the next round!");

    // Move Tiles from Pattern to Wall, and empty Floor for each player

    for (PlayerBoardController p: playerBoardControllers) {

      List<Tile> returnTilesWall = p.moveTilesToWall();
      game.getTilecontainer().addDiscardedTiles(returnTilesWall);

//      System.out.println("Wall score: " + p.getFloorController().getFloor().getScore());
      p.updateScore();
      List<Tile> returnTilesFloor = p.removeTilesFromFloor();
      if (returnTilesFloor.contains(Tile.STARTING)) {
        returnTilesFloor.remove(Tile.STARTING);
      }
      game.getTilecontainer().addDiscardedTiles(returnTilesFloor);

    }

    // Re-fill Factories

    for (FactoryController f: factoryControllers) {
      try {
        List<Tile> tilesToAdd = game.getTilecontainer().grabBagTiles(4);
        f.getFactory().addTiles(tilesToAdd);
      } catch (FullException e) {
        throw new RuntimeException(e);
      }
    }
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




  @Override
  public List<Tile> moveTilesToWall() {
    return null;
  }

  @Override
  public List<Tile> removeTilesFromFloor() {
    return null;
  }

  @Override
  public void moveTilesToFloor(List<Tile> tiles) {

  }

  @Override
  public void moveTilesToPattern(List<Tile> tiles) {

  }

  @Override
  public void moveTilesToTable(List<Tile> tiles){
    try {
      tableController.addTilesToTable(tiles);
    } catch (FullException ignore){

    }
  }

  @Override
  public void moveTilesToTileContainer(Tile tile) {
    this.getGame().getTilecontainer().addDiscardedTiles(Collections.singletonList(tile));

  }

  @Override
  public void updateScore() {

  }

  @Override
  public void removeTilesFromTable() {
//    tableController.removeSelectedTilesFromTable();
  }

//  @Override
//  public void removeTilesFromFactory(List<Tile> tiles, FactoryController factoryController) {
//    try {
//      tableController.addTilesToTable(tiles);
//    } catch (FullException ignore){
//
//    }
//    factoryController.emptyFactory();
//  }
}
