package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameController {

  @FXML
  private TextField usernameTextField;

  @FXML
  private Label welcomeText;

  public void onStartGameButtonClick() {
    welcomeText.setText("Welcome " + usernameTextField.getText() + "!");
  }
}
