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
    Factory factory;

    // view
    @FXML
    GridPane tileGrid;

    @Getter
    @Setter
    private GameBoardController gameBoardController;

//    public void onTileClick(ActionEvent event) {
//        ClickableTile tile = (ClickableTile) event.getSource();
//        Tile selected_tile = tile.getColour();
//        String selected_colour = selected_tile.toString();
//        factory.grabTiles(selected_tile);
//        setSelectedTiles(selected_colour);
//    }

    /**
     * Used to update the view if the model changes
     */
    public void setTileColours(List<Tile> colours) {
        for(Integer i = 0; i < colours.size(); i++) {
            Tile colour = colours.get(i);
            ClickableTile tile = (ClickableTile) tileGrid.getChildren().get(i);
            tile.getStyleClass().clear();
            tile.getStyleClass().add(String.valueOf(colour));
            tile.setColour(colour);
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
        for(Node tile : tileGrid.getChildren()) {
            ObservableList<String> style = tile.getStyleClass();
            if(style.contains(colour)) {
                style.add("selected");
            }
        }
    }

    public void deselectAllTiles() {
        for (Node node : tileGrid.getChildren()) {
            ObservableList<String> styleClass = node.getStyleClass();
            if (styleClass.contains("selected")) {
                System.out.println(node.getStyleClass());
                styleClass.remove("selected");
                System.out.println("Removed style from Tile");
                System.out.println(node.getStyleClass());
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
                System.out.println("----------------");
                Tile clickedColor = clickableTile.getColour();
                System.out.println("Selected Tile: " + clickedColor);

                // First deselect all Factories
                System.out.println("Deselecting all factories!");
                gameBoardController.deselectAllFactories();

                // Notify the active PlayerBoard that a Tile has been selected
                highlightTiles(clickedColor);
                gameBoardController.highlightCurrentPlayerBoard(clickedColor, factory);

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
//        this.factory.getSelected_colour().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                String colour = FactoryController.this.factory.getSelected_colour().getValue();
//                FactoryController.this.selectTiles(Tile.valueOf(colour));
//            }
//        });

    }
}
