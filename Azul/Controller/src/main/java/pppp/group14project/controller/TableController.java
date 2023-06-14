package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;


import java.util.List;

public class TableController {

    @FXML
    private GridPane tableGridPane;

    @Setter
    @Getter
    Table table;

    @Setter
    @Getter
    private GameBoardController gameBoardController;


    public void addTilesToTable(List<Tile> tiles) throws FullException {
        this.table.addTiles(tiles);
        displayTilesOnTheTable();
    }

    private void displayTilesOnTheTable() {
        List<Tile> tiles = table.getAllCurrentTiles();
        zeroTableView();
        if (tiles.isEmpty()) {
            return;
        }

        ClickableTile node = null; // Declare node variable outside the loop

        int currentRow = 0;

        for (Tile tile : tiles) {
            if (tile == Tile.STARTING) {
                node = (ClickableTile) tableGridPane.getChildren().get(0);
                node.getStyleClass().add("STARTING");
                node.setOpacity(1);
                node.setColour(tile);
                node.setText("1");
            } else {
                double opacity = 1;
                while (opacity == 1 && currentRow < tableGridPane.getChildren().size()) {
                    currentRow++;
                    node = (ClickableTile) tableGridPane.getChildren().get(currentRow);
                    opacity = node.getOpacity();
                    node.setColour(tile);
                }

                if (node != null) {
                    node.getStyleClass().add(String.valueOf(tile));
                    node.setOpacity(1);
                }
            }
        }
    }


    private void zeroTableView() {
        for (Node node : tableGridPane.getChildren()) {
            node.setOpacity(0);
            ClickableTile clickableTile = (ClickableTile) node;
            clickableTile.removeColour();
        }
    }


    public void setSelectedTiles(Tile clickedTile){
        if (clickedTile == null){
            return;
        }
        String colour = clickedTile.toString();
        for (Node node : tableGridPane.getChildren()){
            ObservableList<String> style = node.getStyleClass();
            if(style.contains(colour) || style.contains("STARTING")) {
                style.add("selected");
            } else {
                style.remove("selected");
            }
        }
    }

    public void unSetSelectedTiles(Tile clickedTile){
        if (clickedTile == null){
            return;
        }
        for (Node node : tableGridPane.getChildren()){
            ObservableList<String> style = node.getStyleClass();
            style.remove("selected");
        }
    }

    public void grabTilesFromTable(Tile tile) throws EmptyException {
        setSelectedTiles(tile);
        if (table.isStartingTileOnTable()){
            table.grabTiles(Tile.STARTING);
        }
        this.table.grabTiles(tile);
//        playerBoardController.moveTilesToPattern();
        zeroTableView();
        displayTilesOnTheTable();

    }


    @SneakyThrows
    public void postInitialize() {
        this.table = new Table();

        System.out.println("Created event listeners for table");


        this.table.getTiles().addListener((ListChangeListener<Tile>) change -> {
            displayTilesOnTheTable();
        });

        for (Node node : tableGridPane.getChildren()) {
            ClickableTile clickableTile = (ClickableTile) node;

            clickableTile.setOnMouseEntered(event-> {
                setSelectedTiles(clickableTile.getColour());
            });

            clickableTile.setOnMouseExited(event-> {
                unSetSelectedTiles(clickableTile.getColour());
            });

            clickableTile.setOnMouseClicked(event -> {
                // Handle the tile click event here
                try {
                    Tile clickedTile = clickableTile.getColour();
                    System.out.println(clickedTile);
                    grabTilesFromTable(clickedTile);
                } catch (EmptyException e) {
                    // Handle the EmptyException if necessary
                    e.printStackTrace();
                }
            });
        }

    }
}

