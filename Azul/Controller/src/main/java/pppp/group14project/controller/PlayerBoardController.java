package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerBoardController implements Initializable {
  @FXML
  public Label playerName;

  @FXML
  private GridPane playerBoardGrid;

  public void setPlayerName(String playerName) {
    this.playerName.setText(playerName);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
      GridPane playerFloor = loader.load();
//      FloorController floorController = loader.getController();
      playerBoardGrid.add(playerFloor, 0, 2);

    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }
}
