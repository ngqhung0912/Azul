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

    welcomeText.setText("Welcome " + game.getUsernameList() + "!");
  }

  public void onStartGameButtonClick() {
    System.out.println("Start game button clicked!");
    // TODO: switch to game board scene using scene manager
  }
}
