package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.EmptyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    List<Factory> factories = game.getFactoryList();
    factories.add(new Factory());
    factories.add(new Factory());
    welcomeText.setText("Welcome " + game.getPlayerNameList() + "!");
  }

  public void onStartGameButtonClick() {
    Game game = Game.getInstance();
    List<Factory> factories = game.getFactoryList();
    factories.add(new Factory());
    TileContainer container = game.getTilecontainer();
    try {
      game.fillFactories(container.grabBagTiles(game.getFactoryList().size()*4));
    } catch (EmptyException e) {
      e.printStackTrace();
    }

    try {
      Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
      usernameTextField.getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
