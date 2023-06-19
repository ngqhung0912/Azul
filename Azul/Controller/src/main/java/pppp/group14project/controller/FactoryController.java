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
import pppp.group14project.model.*;
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

    public void setSelectedTiles(Tile selectedTile) {
        System.out.println("selected: " + selectedTile);
        String colour = selectedTile.toString();
        for(Node tile : tileGrid.getChildren()) {
            ObservableList<String> style = tile.getStyleClass();
            if(style.contains(colour)) {
                style.add("selected");
            } else {
                style.remove("selected");
            }
        }
    }

//    public void onTileClick(ActionEvent event) {
//        ClickableTile tile = (ClickableTile) event.getSource();
//        Tile selected_tile = tile.getColour();
//        String selected_colour = selected_tile.toString();
//        factory.grabTiles(selected_tile);
//        setSelectedTiles(selected_colour);
//    }

    public void setTileColours(ObservableList<Tile> colours) {
        for(Integer i = 0; i < colours.size(); i++) {
            Tile colour = colours.get(i);
            ClickableTile tile = (ClickableTile) tileGrid.getChildren().get(i);
            tile.getStyleClass().clear();
            tile.getStyleClass().add(String.valueOf(colour));
            tile.setColour(colour);
        }
    }

    public void postInitialize() {
        // Listen for tiles added to the factory by the container
        this.factory.getTiles().addListener((ListChangeListener<Tile>) change -> {
            setTileColours(this.factory.getTiles());
        });

        for (Node node : tileGrid.getChildren()) {
            ClickableTile clickableTile = (ClickableTile) node;

            clickableTile.setOnMouseClicked(event -> {
                // Handle the tile click event here
                Tile colour = clickableTile.getColour();
                setSelectedTiles(colour);
                factory.grabTiles(colour);
            });
        }


        // Just to test changing the model (that this can be seen in the view)
//        try {
//            this.factory.empty();
//
//            TileContainer t = this.gameBoardController.getGame().getTilecontainer();
//            List<Tile> tilesForFactory = t.grabBagTiles(4);
//            this.factory.addTiles(tilesForFactory);
//            System.out.println("reached");
//        } catch (FullException e) {
//            e.printStackTrace();
//        }

        // listen to which tile has been selected
        this.factory.getSelected_colour().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String colour = FactoryController.this.factory.getSelected_colour().getValue();
                FactoryController.this.setSelectedTiles(Tile.valueOf(colour));
            }
        });

    }
}
