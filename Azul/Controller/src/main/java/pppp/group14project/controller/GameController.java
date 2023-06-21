package pppp.group14project.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.EmptyException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

  @FXML
  private TextField usernameTextField;

  @FXML
  private Label welcomeText;

  @FXML
  private Button addPlayerButton;

  public void onAddPlayerButtonClick() {
    Game game = Game.getInstance();
    Player player = new Player(usernameTextField.getText());
    Board board = new Board(player);
    game.addBoard(board);
    List<Factory> factories = game.getFactoryList();
    factories.add(new Factory());
    factories.add(new Factory());
    String welcome_text = "Welcome ";
    List<String> players = game.getPlayerNameList();
    for(int i = 0; i < players.size(); i++) {
      if(i > 0) {
        welcome_text += ", ";
      }
      if (players.get(i) != null && !players.get(i).equals("")) {
        welcome_text += players.get(i);
      } else {
        welcome_text += "anonymous";
      }
    }
    welcomeText.setText(welcome_text + "!");

    usernameTextField.setText(null);

    if(game.getPlayerNameList().size() == 4) {
      addPlayerButton.setDisable(true);
    }
  }

  public void onStartGameButtonClick() {
    Game game = Game.getInstance();
    List<Factory> factories = game.getFactoryList();
    factories.add(new Factory());
    TileContainer container = game.getTilecontainer();

    try {
      Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
      usernameTextField.getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Font.loadFont(getClass().getResource("/Binner.ttf").toExternalForm(), 10);

  }
}
