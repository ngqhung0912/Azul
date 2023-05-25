package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pppp.group14project.model.Board;
import pppp.group14project.model.Game;
import pppp.group14project.model.Player;

import java.io.IOException;

public class GameController {

  @FXML
  private TextField usernameTextField;

  @FXML
  private Label welcomeText;

  public void onAddPlayerButtonClick() {

    Game game = Game.getInstance();
    Player player = new Player(usernameTextField.getText());
    Board board = new Board(player);
    game.addBoard(board);

    welcomeText.setText("Welcome " + game.getPlayerNameList() + "!");
  }

  public void onStartGameButtonClick() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
      usernameTextField.getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
