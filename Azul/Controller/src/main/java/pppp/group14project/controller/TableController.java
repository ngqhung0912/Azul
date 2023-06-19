package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;


import java.util.ArrayList;
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

    @Getter
    private final Integer PlayerID = 1;


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
                node.setAlignment(Pos.CENTER);
                node.setText("1");
            } else {
                double opacity = 1;
                while (opacity == 1 && currentRow < tableGridPane.getChildren().size()) {
                    node = (ClickableTile) tableGridPane.getChildren().get(currentRow);
                    opacity = node.getOpacity();
                    node.setColour(tile);
                    System.out.println(node);
                    currentRow++;
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
            if (!clickableTile.getText().isEmpty()) {
                clickableTile.setText("");
            }
            clickableTile.getStyleClass().clear();
            clickableTile.removeColour();
        }
    }


    private void setSelectedTiles(Tile clickedTile) {
        if (clickedTile == null) {
            return;
        }
        String colour = clickedTile.toString();
        for (Node node : tableGridPane.getChildren()) {
            ObservableList<String> style = node.getStyleClass();
            if (style.contains(colour) || style.contains("STARTING")) {
                style.add("selected");
            } else {
                style.remove("selected");
            }
        }
    }

    private void unSetSelectedTiles(Tile clickedTile) {
        if (clickedTile == null) {
            return;
        }
        for (Node node : tableGridPane.getChildren()) {
            ObservableList<String> style = node.getStyleClass();
            style.remove("selected");
        }
    }

    public void selectTilesToGrabFromTable(Tile tile)  {
        setSelectedTiles(tile);
        List<Tile> selectedTiles = new ArrayList<>();
        if (table.isStartingTileOnTable()) {
            this.table.selectGrabbedTiles(Tile.STARTING);
        }
        //the 1 has to be changed based on the turn
        selectedTiles.addAll(this.table.selectGrabbedTiles(tile));
        gameBoardController.getPlayerBoardControllers().get(getPlayerID()).moveTilesToPattern(selectedTiles);
    }

    public void removeSelectedTilesFromTable() {
        table.removeTiles();
        zeroTableView();
        displayTilesOnTheTable();
        System.out.println(this.table.getAllCurrentTiles());
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

            clickableTile.setOnMouseEntered(event -> {
                setSelectedTiles(clickableTile.getColour());
            });

            clickableTile.setOnMouseExited(event -> {
                unSetSelectedTiles(clickableTile.getColour());
            });

            clickableTile.setOnMouseClicked(event -> {
                // Handle the tile click event here
            Tile clickedTile = clickableTile.getColour();
            System.out.println(clickedTile);
            selectTilesToGrabFromTable(clickedTile);
                    // Handle the EmptyException if necessary
            });
        }

    }
}

