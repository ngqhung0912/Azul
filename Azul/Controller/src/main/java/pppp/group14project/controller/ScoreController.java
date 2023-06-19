package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Setter;
import pppp.group14project.model.Board;

public class ScoreController {

  @FXML
  private Text scoreText;

  @Setter
  private Board board;

  private void setScoreText(String score) {
    scoreText.setText(score);
  }

  /**
   * Initializes the models, once all models of its parent models have loaded
   */
  public void postInitialize() {
    // first initialization
    setScoreText(Integer.toString(board.getScore().intValue()));

    board.getScore().addListener((observableValue, oldValue, newValue) -> {
      setScoreText(Integer.toString(newValue.intValue()));
    });
  }

  public void updateScore() {
    // additional check if currently displayed score is the same as the score in the model
    int displayedScore = Integer.parseInt(scoreText.getText());
    int modelScore = board.getScore().intValue();
    assert displayedScore == modelScore;

    int floorScore = board.getFloor().getScore();
    int wallScore = board.getWall().getWallScore();
    board.updateScore(floorScore + wallScore);
  }
}
