package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private GridPane tableGridPane;

    @Setter
    @Getter
    Table table;

    @Setter
    @Getter
    GameBoardController gameBoardController;

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


    private void zeroTableView() {
        for (Node node : tableGridPane.getChildren()) {
            node.setOpacity(0);
        }
    }

    public void grabTilesFromTable(Tile tile) throws EmptyException {
        if (table.isStartingTileOnTable()){
            table.grabTiles(Tile.STARTING);
        }
        this.table.grabTiles(tile);
        zeroTableView();
        displayTilesOnTheTable();
    }


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.table = new Table();

        System.out.println("Created event listeners for table");

        ClickableTile clickableTile = new ClickableTile();
        clickableTile.setOnMouseClicked(event -> {
            // Handle the tile click event here
            try {
                grabTilesFromTable(clickableTile.getColour());
            } catch (EmptyException ignored) {
                // TODO Handle the EmptyException if necessary
            }
        });

    }
}

