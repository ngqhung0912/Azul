package pppp.group14project.controller;


import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Tile;

import java.util.List;


public class FactoryController {

    // model
    @Setter
    @Getter
    private Factory factory;

    // view
    @FXML
    private GridPane tileGrid;

    @Getter
    @Setter
    private GameBoardController gameBoardController;

    /**
     * Used to update the view if the model changes
     */
    public void setTileColours(List<Tile> colours) {
        for (Integer i = 0; i < colours.size(); i++) {
            Tile colour = colours.get(i);
            ClickableTile tile = (ClickableTile) tileGrid.getChildren().get(i);
            tile.getStyleClass().clear();
            tile.getStyleClass().add(String.valueOf(colour));
            tile.setColour(colour);
            tile.setVisible(true);
        }
        // Hide the other ClickableTiles that are not colored
        int i = colours.size();
        while (i < 4) {
            ClickableTile tile = (ClickableTile) tileGrid.getChildren().get(i);
            tile.setVisible(false);
            i++;
        }
    }

    public void highlightTiles(Tile selectedTile) {
        String colour = selectedTile.toString();
        for (Node tile : tileGrid.getChildren()) {
            ObservableList<String> style = tile.getStyleClass();
            if (style.contains(colour)) {
                style.add("selected");
            }
        }
    }

    public void deselectAllTiles() {
        for (Node node : tileGrid.getChildren()) {
            ObservableList<String> styleClass = node.getStyleClass();
            if (styleClass.contains("selected")) {
                styleClass.remove("selected");
            }
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
                Tile clickedColor = clickableTile.getColour();

                // First deselect all Factories
                gameBoardController.deselectAllFactories();

                // Notify the active PlayerBoard that a Tile has been selected
                highlightTiles(clickedColor);
                gameBoardController.highlightCurrentPlayerBoard(clickedColor, factory);

            });
        }
    }
}
