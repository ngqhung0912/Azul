package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class FactoriesController {
    @FXML
    private GridPane factoryGridPane;

    private int players;

    public void setNumberOfPlayers(int numberOfPlayers) { this.players = numberOfPlayers; }


}
