package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Board;
import pppp.group14project.model.PatternLine;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerBoardController implements Initializable, Mediator {

  /**
   * FXML for updating the View
   */
  @FXML
  public Label playerName;
  @FXML
  private GridPane playerBoardGrid;

  /**
   * Player board model
   */
  @Setter
  @Getter
  private Board board;

  /**
   * References to other controllers
   */
  @Setter
  @Getter
  private PatternController patternController;
  @Setter
  @Getter
  private FloorController floorController;
  @Setter
  @Getter
  private WallController wallController;
  @Setter
  @Getter
  private GameBoardController gameBoardController;

  public void setPlayerName(String playerName) {
    this.playerName.setText(playerName);
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader floorLoader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
      FXMLLoader patternLoader = new FXMLLoader(getClass().getResource("/player-pattern-view.fxml"));
      FXMLLoader scoreLoader = new FXMLLoader(getClass().getResource("/board-score-view.fxml"));
      FXMLLoader wallLoader = new FXMLLoader(getClass().getResource("/player-wall-view.fxml"));
      StackPane playerPattern = patternLoader.load();
      GridPane playerFloor = floorLoader.load();
      AnchorPane playerScore = scoreLoader.load();
      GridPane playerWall = wallLoader.load();

      playerBoardGrid.add(playerPattern, 0, 1);
      playerBoardGrid.add(playerFloor, 0, 2);
      playerBoardGrid.add(playerScore, 2,3);
      playerBoardGrid.add(playerWall, 1, 1);

      patternController = patternLoader.getController();
      floorController = floorLoader.getController();
      patternController.setPlayerBoardController(this);
      floorController.setPlayerBoardController(this);
      wallController = wallLoader.getController();
      wallController.setPlayerBoardController(this);
    } catch (
            IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the models, once all models of its parent models have loaded
   */
  public void postInitialize() {

    floorController.setPlayerBoardController(this);
    floorController.setFloor(board.getFloor());
    floorController.postInitialize();

    patternController.setPlayerBoardController(this);
    patternController.setPattern(board.getPattern());
    patternController.postInitialize();

    wallController.setPlayerBoardController(this);
    wallController.setWall(board.getWall());
    wallController.postInitialize();

  }

  /**
   * Concrete Mediator implementation of moving tiles between different GameBoard components
   */


  /**
   * Activates this PlayerBoard for interactivity, and highlights possible spaces for some passed tiles
   * @param tiles
   */
  public void activate(List<Tile> tiles) throws InvalidPositionException {
    patternController.highlightPossibleSpaces(tiles);
  }

  /**
   * Method which is called by the GameBoardController after every round to move tiles from Pattern to Wall
   * @param tile
   * @param rowNumber
   */
  @Override
  public void moveTilesToWall(Tile tile, int rowNumber) {

    List<PatternLine> patternLines = patternController.getPattern().getPatternLines();

    try {
      for (int i = 0; i < patternLines.size(); i++) {
        PatternLine patternLine = patternLines.get(i);
        if (patternLine.isFull()) {
          List<Tile> tilesToMove = patternLine.getSpaces();
          Tile wallTile = tilesToMove.remove(0);
          wallController.addTileToWall(wallTile, i);

          // TODO: Move remaining tiles to discardedTiles in TileContainer

          patternLine.empty();
        }
      }
    } catch (FullException ignored) {
//      throw new RuntimeException(e);
      // TODO PLEASE NEVER THROW A FUCKING RUNTIME EXCEPTION!!!!!!
    }

  }

  /**
   * Method which is called by Pattern to move tiles to Floor immediately once tiles are placed
   * @param tiles
   */
  @Override
  public void moveTilesToFloor(List<Tile> tiles) {
    floorController.addTilesToFloor(tiles);
  }

  @Override
  public void moveTilesToPattern(List<Tile> tiles) {
    // TODO: not implemented in the player board mediator
  }

  @Override
  public void moveTilesToTable(List<Tile> tiles) {
    // TODO: not implemented in the player board mediator
  }
}
