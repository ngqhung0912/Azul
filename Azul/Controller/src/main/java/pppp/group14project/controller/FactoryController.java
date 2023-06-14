package pppp.group14project.controller;

import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Game;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FactoryController {

    // model
    @Setter
    @Getter
    Factory factory;

    // view
    @FXML
    GridPane tileGrid;

    @Getter
    @Setter
    private GameBoardController gameBoardController;

    public void setSelectedTiles(String colour) {
        for(Node tile : tileGrid.getChildren()) {
            ObservableList<String> style = tile.getStyleClass();
            if(style.contains(colour)) {
                style.add("selected");
            } else {
                style.remove("selected");
            }
        }
    }

    public void onTileClick(ActionEvent event) {
        String selected_colour = ((ClickableTile) event.getSource()).getStyleClass().get(0);
        Tile selected_tile = Tile.valueOf(selected_colour);
        factory.grabTiles(selected_tile);
        setSelectedTiles(selected_colour);
    }

    public void setTileColours(ObservableList<Tile> colours) {
        for(Integer i = 0; i < colours.size(); i++) {
            Tile colour = colours.get(i);
            Node tile = tileGrid.getChildren().get(i);
            tile.getStyleClass().clear();
            tile.getStyleClass().add(String.valueOf(colour));
        }
    }

    public void postInitialize() {
        this.factory.getTiles().addListener((ListChangeListener<Tile>) change -> {
            setTileColours(this.factory.getTiles());
        });

        // Just to test changing the model (that this can be seen in the view)
        try {
            this.factory.empty();
            this.factory.addTiles(Arrays.asList(new Tile[]{Tile.BLACK, Tile.ORANGE, Tile.BLACK, Tile.RED}));
        } catch (FullException e) {
            e.printStackTrace();
        }

        this.factory.getSelected_colour().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                FactoryController.this.setSelectedTiles(FactoryController.this.factory.getSelected_colour().toString());
            }
        });

    }
}
