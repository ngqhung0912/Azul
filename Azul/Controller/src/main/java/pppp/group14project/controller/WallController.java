package pppp.group14project.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;


public class WallController {

    @FXML
    private GridPane wallGridPane;
    private Button[][] spaces = new Button[5][5];

    @Getter
    @Setter
    private Wall wall;

    /**
     * References to other controllers
     */
    @Setter
    @Getter
    private PlayerBoardController playerBoardController;


    /**
     * Adds a tile to a wall
     *
     * @param tile the tile that should be added
     * @param row  on which row it should be added
     */
    public void addTileToWall(Tile tile, int row) throws FullException, WrongTileException {
        wall.addTile(tile, row);
    }


    public boolean wallContainsTile(Tile tile, int row) {
        return wall.isTileInRow(tile, row);
    }

    /**
     * Resets all the tiles back to original look
     */
    public void resetWallView() {
        wall.emptyWall();
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().clear();
                int rowID = GridPane.getRowIndex(node);
                int columnID = GridPane.getColumnIndex(node);
                Tile color = Wall.getTileColor(rowID, columnID);
                node.getStyleClass().add(color.toString());
                node.getStyleClass().add("is-not-colored");
            }
        }
    }

    public Button getSpace(int row, int column) {
        return spaces[row][column];
    }

    public void postInitialize() {

        // Create a 2D list from the 1D list of children, to make it easier to find the spaces
        for (Node n : wallGridPane.getChildren()) {
            int rowIndex = GridPane.getRowIndex(n);
            int columnIndex = GridPane.getColumnIndex(n);
            spaces[rowIndex][columnIndex] = (Button) n;
        }

        // Reset wall to give it the correct colors from the model
        resetWallView();

        // Rerender view if model changes
        for (int i = 0; i < 5; i++) {
            ObservableList row = wall.getWall().get(i);
            int rowNumber = i;
            row.addListener((ListChangeListener<Tile>) change -> {
                while (change.next()) {
                    if (change.wasReplaced() || change.wasAdded()) {
                        for (int columnNumber = change.getFrom(); columnNumber < change.getTo(); ++columnNumber) {
                            Button b = getSpace(rowNumber, columnNumber);
                            b.getStyleClass().remove("is-not-colored");
                            b.getStyleClass().add("is-colored");
                        }
                    }
                }
            });
        }
    }

}
