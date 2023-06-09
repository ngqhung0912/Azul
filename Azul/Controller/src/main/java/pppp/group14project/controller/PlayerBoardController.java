package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader floorLoader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
      FXMLLoader patternLoader = new FXMLLoader(getClass().getResource("/player-pattern-view.fxml"));
      StackPane playerPattern = patternLoader.load();
      GridPane playerFloor = floorLoader.load();

      playerBoardGrid.add(playerPattern, 0, 1);
      playerBoardGrid.add(playerFloor, 0, 2);

      patternController = patternLoader.getController();
      floorController = floorLoader.getController();
    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }




}
