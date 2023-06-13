package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Table;

public class FactoriesController {
    @FXML
    private GridPane factoryGridPane;

    @Setter
    GameBoardController gameBoardController;

    @Setter
    @Getter
    Factory factory;

    private int players;

    public void setNumberOfPlayers(int numberOfPlayers) { this.players = numberOfPlayers; }

    public void postInitialize(){
        //
    }




}
