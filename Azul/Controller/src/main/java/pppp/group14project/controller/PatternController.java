package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Pattern;
import pppp.group14project.model.PatternLine;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternController {

    /**
     * FXML for updating views
     */
    @FXML
    private VBox rows;

    /**
     * Pattern data model
     */
    @Setter
    @Getter
    private Pattern pattern;

    /**
     * References to other controllers
     */
    @Setter
    @Getter
    private PlayerBoardController playerBoardController;

    /**
     * Check if pattern have possible spaces
     */
    private boolean patternHasPossibleSpaces(Tile tileToAdd) {
        for (int i = 0; i < 5; i++) {
            PatternLine p = pattern.getPatternLines().get(i);
            if ((p.isEmpty() || (p.getTileType() == tileToAdd && !p.isFull())) && !playerBoardController.wallContainsTile(tileToAdd, i)) {
                return true;
            }
        }
        return false;
    }



    public void highlightPossibleSpaces(Tile tile, Factory factory) throws InvalidPositionException {
        unhighlightAllSpaces();

        if (patternHasPossibleSpaces(tile)) {
            for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
                // Go to next row if the row has a tile, but it is not equal to the tile color given
                if (tileDoesNotFitsRow(tile, rowIndex))
                    continue;

                for (int tileIndex = 0; tileIndex <= rowIndex; tileIndex++) {
                    if (!spaceHasTile(rowIndex, tileIndex)) {
                        highlightSpace(rowIndex, tileIndex, tile, factory);
                        break;
                    }
                }
            }
        } else {
            moveTilesWhenPatternHasNoSpace(tile, factory);
        }
    }

    private boolean tileDoesNotFitsRow (Tile tile, int row) throws InvalidPositionException {
        return (rowHasTile(row) && !rowHasTile(row, tile) )|| playerBoardController.wallContainsTile(tile, row);
    }

    /**
     * Move tiles to Table and floor when pattern has no space
     */
    private void moveTilesWhenPatternHasNoSpace(Tile tile, Factory factory) {
        List<List<Tile>> returnTiles = factory.grabTiles(tile);
        List<Tile> grabbedTiles = returnTiles.get(0);
        playerBoardController.moveTilesToFloor(grabbedTiles);

        List<Tile> tableTiles = returnTiles.get(1);
        playerBoardController.moveTilesToTable(tableTiles);

        playerBoardController.getGameBoardController().finishPlayerTurn();
        unhighlightAllSpaces();
    }

    /**
     * Gets a Space
     * @param rowNumber   row number, starting from 0 at the top
     * @param indexNumber index number, starting from 0 at the right
     * @return
     * @throws InvalidPositionException
     */
    private Space getSpace(int rowNumber, int indexNumber) throws InvalidPositionException {
        Space space;
        try {
            HBox row = (HBox) rows.getChildren().get(rowNumber);
            int numberOfSpaces = row.getChildren().size();
            space = (Space) row.getChildren().get(numberOfSpaces - 1 - indexNumber);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidPositionException();
        }
        return space;
    }

    private void highlightSpace(int rowNumber, int indexNumber, Tile tile, Factory factory) throws InvalidPositionException {
        Space s = getSpace(rowNumber, indexNumber);
        s.getStyleClass().add("tile-option");
        s.setOnAction(e -> {
            try {
                patternMoveTiles(factory, tile, rowNumber);
            } catch (WrongTileException ex) {
                throw new RuntimeException(ex);
            }
            playerBoardController.getGameBoardController().finishPlayerTurn();
            unhighlightAllSpaces();
        });
    }

    public void patternMoveTiles(Factory factory, Tile tile, int rowNumber) throws WrongTileException {
        // Grab from Factory model or Table
        List<List<Tile>> returnTiles = factory.grabTiles(tile);
        List<Tile> grabbedTiles = returnTiles.get(0);
        List<Tile> tableTiles = returnTiles.get(1);

        playerBoardController.moveTilesToTable(tableTiles);

        patternHandleStarting(grabbedTiles, playerBoardController);

        List<Tile> excessTiles = this.pattern.addTiles(rowNumber, grabbedTiles);
        playerBoardController.moveTilesToFloor(excessTiles);

    }

    private void patternHandleStarting(List<Tile> grabbedTiles, PlayerBoardController playerBoardController) {
        if (grabbedTiles.contains(Tile.STARTING)) {
            grabbedTiles.remove(Tile.STARTING);
            List<Tile> startingTile = new ArrayList<>();
            startingTile.add(Tile.STARTING);
            playerBoardController.moveTilesToFloor(startingTile);
        }
    }

    private void unhighlightAllSpaces() {
        for (Node row : rows.getChildren()) {
            HBox r = (HBox) row;
            for (Node space : r.getChildren()) {
                Button b = (Button) space;
                b.setOnAction(null);

                if (space.getStyleClass().contains("tile-option")) {
                    b.getStyleClass().remove("tile-option");
                }
            }
        }
    }

    private boolean spaceHasTile(int rowNumber, int indexNumber) throws InvalidPositionException {
        return getSpace(rowNumber, indexNumber).getStyleClass().contains("is-colored");
    }

    private boolean rowHasTile(int rowNumber, Tile tileColor) throws InvalidPositionException {
        return getSpace(rowNumber, 0).getStyleClass().contains(String.valueOf(tileColor));
    }

    private boolean rowHasTile(int rowNumber) throws InvalidPositionException {
        return getSpace(rowNumber, 0).getStyleClass().contains("is-colored");
    }

    private void resetTiles(int rowNumber) throws InvalidPositionException {
        int numberOfTiles = rowNumber + 1;

        for (int i = 0; i < numberOfTiles; i++) {

            Space s = getSpace(rowNumber, i);
            s.getStyleClass().clear();
            s.getStyleClass().add("button");
            s.getStyleClass().add("pattern-tile-box");
        }

    }

    private void setTiles(int rowNumber, int numberOfTiles, Tile tileColor) throws InvalidPositionException {
        for (int i = 0; i < numberOfTiles; i++) {
            Space s = getSpace(rowNumber, i);
            s.getStyleClass().add(String.valueOf(tileColor));
            s.getStyleClass().add("is-colored");
        }
    }


    /**
     * Initializes the models, once all models of its parent models have loaded
     */
    public void postInitialize() {

        // Register event handlers here::
        // Create change listener that listens to changes in the model and updates the view
        for (PatternLine p : pattern.getPatternLines()) {
            p.getSpaces().addListener((ListChangeListener<Tile>) change -> {
                /**
                 * HERE IS WHERE YOU SHOULD DEFINE EVENT LISTENERS TO RERENDER YOUR VIEW,
                 * PROBABLY USING SOME METHOD LIKE SET_SPACES() WHICH UPDATES ALL OF THE TILE VIEWS
                 */
                for (int rowNumber = 0; rowNumber < pattern.getPatternLines().size(); rowNumber++) {
                    int numberOfTiles = pattern.getPatternLines().get(rowNumber).numberOfFullSpaces();
                    Tile tileColor = pattern.getPatternLines().get(rowNumber).getTileType();
                    try {
                        // Update views
                        resetTiles(rowNumber);
                        setTiles(rowNumber, numberOfTiles, tileColor);
                    } catch (InvalidPositionException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }

}

