package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import pppp.group14project.model.Board;

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

  public void updateScore(Board board) {
    // additional check if currently displayed score is the same as the score in the model
    int displayedScore = Integer.parseInt(scoreText.getText());
    int modelScore = board.getScore();
    assert displayedScore == modelScore;

    int floorScore = board.getFloor().getScore();
    int wallScore = board.getWall().getWallScore();
    board.setScore(modelScore + floorScore + wallScore);
    setScoreText(Integer.toString(board.getScore()));
  }
}
