package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ScoreController implements Initializable {

  @FXML
  private Text scoreText;

  public void setScoreText(String score) {
    scoreText.setText(score);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setScoreText("0");
  }
}
