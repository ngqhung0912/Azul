package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
  @FXML
  private GridPane gameBoardGrid;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("player-board-view.fxml"));
      VBox playerBoard1 = loader.load();
      VBox playerBoard2 = loader.load();
      gameBoardGrid.add(playerBoard1, 0, 0);
      gameBoardGrid.add(playerBoard2, 0, 300);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
