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
import pppp.group14project.model.Board;
import pppp.group14project.model.Tile;

import java.io.IOException;
import java.net.URL;
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

  private PatternController patternController;
  private FloorController floorController;

  public void setPlayerName(String playerName) {
    this.playerName.setText(playerName);
  }

  public void attachComponentModels() {
    // attach models to respective controllers
    patternController.setPattern(board.getPattern());
    floorController.setFloor(board.getFloor());

    // attach mediator
    patternController.setMediator(this);
    floorController.setMediator(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader floorLoader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
      FXMLLoader patternLoader = new FXMLLoader(getClass().getResource("/player-pattern-view.fxml"));
      FXMLLoader scoreLoader = new FXMLLoader(getClass().getResource("/board-score-view.fxml"));
      FXMLLoader wallLoader  = new FXMLLoader(getClass().getResource("/player-wall-view.fxml"));
      StackPane playerPattern = patternLoader.load();
      GridPane playerFloor = floorLoader.load();
      AnchorPane playerScore = scoreLoader.load();
      GridPane playerWall = wallLoader.load();

      playerBoardGrid.add(playerPattern, 0, 1);
      playerBoardGrid.add(playerFloor, 0, 2);
      playerBoardGrid.add(playerScore, 1,3);
      playerBoardGrid.add(playerWall, 1, 1 );

      patternController = patternLoader.getController();
      floorController = floorLoader.getController();
    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Concrete Mediator implementation of moving tiles between different GameBoard components
   */

  @Override
  public void moveTilesToWall(Tile tile) {
    // TODO: implement
  }

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
