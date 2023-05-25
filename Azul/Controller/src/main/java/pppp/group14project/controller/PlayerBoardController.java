package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerBoardController {
  @FXML
  public Label playerName;

  public void setPlayerName(String playerName) {
    this.playerName.setText(playerName);
  }
}
