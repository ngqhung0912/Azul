package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FactoryController implements Initializable {

    // model
    Factory factory;

    // view
    @FXML
    GridPane tileGrid;

    public void onTileClick(ActionEvent event) {
        String selected_colour = ((ClickableTile) event.getSource()).getStyleClass().get(0);
        Tile selected_tile = Tile.valueOf(selected_colour);
        try {
            factory.grabTiles(selected_tile);
        } catch (EmptyException e) {
            e.printStackTrace();
        }

        for(Node tile : tileGrid.getChildren()) {
            if(tile.getStyleClass().contains(selected_colour)) {
                tile.getStyleClass().add("selected");
            }
        }

    }

    public void setTileColours(ObservableList<Tile> colours) {
        for(Integer i = 0; i < colours.size(); i++) {
            Tile colour = colours.get(i);
            Node tile = tileGrid.getChildren().get(i);
            tile.getStyleClass().clear();
            tile.getStyleClass().add(String.valueOf(colour));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.factory = new Factory();

        factory.getTiles().addListener((ListChangeListener<Tile>) change -> {
            setTileColours(factory.getTiles());
        });

        // Just to test changing the model (that this can be seen in the view)
        try {
            this.factory.addTiles(Arrays.asList(new Tile[]{Tile.BLACK, Tile.ORANGE, Tile.BLACK, Tile.RED}));
        } catch (FullException e) {
            e.printStackTrace();
        }

    }




}
