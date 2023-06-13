package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;


public class WallController {

    @FXML
    private GridPane wallGridPane;

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
     * @param tile the tile that should be added
     * @param row on which row it should be added
     */
    public void addTileToWall(Tile tile, int row) {
        Rectangle tileToWall = findTileLocationInRow(row, tile);
        if (tileToWall != null) {
            int column = GridPane.getColumnIndex(tileToWall);
            tileToWall.setOpacity(1.0);
            tileToWall.setStrokeWidth(3);
            wall.addTile(tile, row, column);
        }
    }


    /**
     * Finds a Rectangle in GridPane for a given tile in a row
     * @param rowNumber the number of the row for finding the tile
     * @param tile the tile of which location is needed
     * @return the Rectangle associated with that tile
     */
    public Rectangle findTileLocationInRow(int rowNumber, Tile tile) {
        String color = tile.toString();
        Rectangle tileToWall = null;
        for (Node node : wallGridPane.getChildren()) {
            double opacityNoTile = 0.5;
            if (node.getId().equals(color)
                    && GridPane.getRowIndex(node) == rowNumber
                    && node.getOpacity() == opacityNoTile) {
                tileToWall = (Rectangle) node;
            }
        }
        return tileToWall;
    }

    /**
     * Resets all the tiles back to original look
     */
    public void resetWallView() {
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().clear();
                int rowID = GridPane.getRowIndex(node);
                int columnID = GridPane.getColumnIndex(node);
                Tile color = Wall.getTileColor(rowID, columnID);
                node.getStyleClass().add(color.toString());
                node.getStyleClass().add("is-not-colored");
                System.out.println(node.getStyleClass());
            }
        }
    }

    public void getRectangle(int row, int column) {

    }

    public void postInitialize(){

        resetWallView();

        for (wall)

    }
}
