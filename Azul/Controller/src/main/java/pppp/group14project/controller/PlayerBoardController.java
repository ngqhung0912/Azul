package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerBoardController implements Initializable {

  /**
   * FXML for updating the View
   */
  @FXML
  public Label playerName;
  @FXML
  private GridPane playerBoardGrid;
  @FXML
  public StackPane pattern;


  public void setPlayerName(String playerName) {
    this.playerName.setText(playerName);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader floorLoader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
      FXMLLoader patternLoader = new FXMLLoader(getClass().getResource("/player-pattern-view.fxml"));
      StackPane playerPattern = patternLoader.load();
      GridPane playerFloor = floorLoader.load();

//      playerBoardGrid.getChildren().remove(pattern);
//      int rowIndex = GridPane.getRowIndex(pattern);
//      int columnIndex = GridPane.getColumnIndex(pattern);
      playerBoardGrid.add(playerPattern, 0, 1);

      PatternController patternController = patternLoader.getController();

//      FloorController floorController = loader.getController();
      playerBoardGrid.add(playerFloor, 0, 2);

    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }




}
