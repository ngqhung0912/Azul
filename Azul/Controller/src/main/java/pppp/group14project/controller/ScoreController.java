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
        board.getScore().addListener((observableValue, oldValue, newValue) -> {
            setScoreText(Integer.toString(newValue.intValue()));
        });
    }

}
