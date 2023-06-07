package pppp.group14project.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import java.util.List;

public class TableController {

    public void addTilesToTable(Table table, List<Tile> tiles, GridPane tableGridPane) throws FullException {
        table.addTiles(tiles);
        displayTilesOnTheTable(table, tableGridPane);
    }

    private void displayTilesOnTheTable(Table table, GridPane tableGridPane) {
        List<Tile> tiles = table.getAllCurrentTiles();
        zeroTableView(tableGridPane);
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
            } else {
                double opacity = 1;
                while (opacity == 1 && currentRow < tableGridPane.getChildren().size()) {
                    currentRow++;
                    node = (ClickableTile) tableGridPane.getChildren().get(currentRow);
                    opacity = node.getOpacity();
                }

                if (node != null) {
                    node.getStyleClass().add(String.valueOf(tile));
                    node.setOpacity(1);
                }
            }
        }
    }


    private void zeroTableView(GridPane tableGridPane) {
        for (Node node : tableGridPane.getChildren()) {
            node.setOpacity(0);
        }
    }

    public void grabTilesFromTable(Table table, Tile tile, GridPane tableGridPane) throws EmptyException {
        List<Tile> grabbedTiles = table.grabTiles(tile);
        zeroTableView(tableGridPane);
        displayTilesOnTheTable(table, tableGridPane);
    }


}

