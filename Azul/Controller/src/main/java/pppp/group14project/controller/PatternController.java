package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
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
import pppp.group14project.model.Pattern;
import pppp.group14project.model.PatternLine;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.WrongTileException;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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

    void highlightPossibleSpaces(List<Tile> tiles) throws InvalidPositionException {
        for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
            // Go to next row if the row has a tile, but it is not equal to the tile color given
            if (rowHasTile(rowIndex) && !rowHasTile(rowIndex, tiles.get(0)))
                continue;

            for (int tileIndex = 0; tileIndex <= rowIndex; tileIndex++) {
                if (!spaceHasTile(rowIndex, tileIndex)) {
                    highlightSpace(rowIndex, tileIndex, tiles);
                    break;
                }
            }
        }
    }

    /**
     * Gets a Space
     * @param rowNumber row number, starting from 0 at the top
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

    private void highlightSpace(int rowNumber, int indexNumber, List<Tile> tiles) throws InvalidPositionException {
        Space s = getSpace(rowNumber, indexNumber);
        s.getStyleClass().add("tile-option");
        s.setOnAction(e -> {
            try {
                /**
                 * Moving tiles after a Space has been clicked on the Pattern
                 */
                List<Tile> excessTiles = this.pattern.addTiles(rowNumber, tiles);
                // Moves tiles to floor immediately
                playerBoardController.moveTilesToFloor(excessTiles);

            } catch (WrongTileException ex) {
                throw new RuntimeException(ex);
            }
            unhighlightAllSpaces();
        });
    }

    private void unhighlightAllSpaces() {
        for (Node row : rows.getChildren()) {
            HBox r = (HBox) row;
            for (Node space : r.getChildren()) {
                if (space.getStyleClass().contains("tile-option")) {
                    Button b = (Button) space;
                    b.setOnAction(null);
                    b.getStyleClass().remove("tile-option");
                }
            }
        }
    }

    private boolean spaceHasTile(int rowNumber, int indexNumber, Tile tileColor) throws InvalidPositionException {
        return getSpace(rowNumber, indexNumber).getStyleClass().contains(String.valueOf(tileColor));
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

    private void setTiles(int rowNumber, int numberOfTiles, Tile tileColor) throws InvalidPositionException {
        System.out.println("Row: " + rowNumber);
        System.out.println("NumTiles: " + numberOfTiles);

        for (int i = 0; i < numberOfTiles; i++) {
            HBox r = (HBox) rows.getChildren().get(rowNumber);
            final int maxNumberOfTiles = r.getChildren().size();
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
                        setTiles(rowNumber, numberOfTiles, tileColor);
                    } catch (InvalidPositionException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Some other methods you can use:
//                while (change.next()) {
//                    if (change.wasAdded()) {
//                        System.out.println(change.getAddedSubList().get(0)
//                                + " was added to the list!");
//                    } else if (change.wasRemoved()) {
//                        System.out.println(change.getRemoved().get(0)
//                                + " was removed from the list!");
//                    }
//                }
            });
        }

//        try {
//            highlightPossibleSpaces(Arrays.asList(Tile.ORANGE, Tile.ORANGE, Tile.ORANGE));
//        } catch (InvalidPositionException e) {
//            throw new RuntimeException(e);
//        }

    }

}

