package pppp.group14project.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
        List<Tile> tiles = table.getTiles();
        if (tiles.isEmpty()){
            return;
        }

        int currentRow = 0;
        if (tiles.get(0) == Tile.STARTING) {
            Rectangle start = (Rectangle) tableGridPane.getChildren().get(0);
            start.getStyleClass().add("STARTING");
            start.setOpacity(1);
            Text text = (Text) tableGridPane.getChildren().get(tableGridPane.getChildren().size() - 1);
            text.setOpacity(1);
            tiles.remove(0);
            currentRow = 1;
        }

        for (Tile tile : tiles) {

            Rectangle node = (Rectangle) tableGridPane.getChildren().get(currentRow);
            if (node.getOpacity() == 0) {
                node.getStyleClass().add(String.valueOf(tile));
                node.setOpacity(1);
            }
            currentRow++;
        }
    }

    private void zeroTableView(GridPane tableGridPane) {
        for (Node node : tableGridPane.getChildren()) {
            node.setOpacity(0);
        }
    }

    public void grabTilesFromTable(Table table, Tile tile, GridPane tableGridPane) throws EmptyException {
        table.grabTiles(tile);
        zeroTableView(tableGridPane);
        displayTilesOnTheTable(table, tableGridPane);
    }



}

