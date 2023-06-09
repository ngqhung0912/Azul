package pppp.group14project.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;

public class WallController {

    /**
     * Adds a tile to a wall
     * @param wall the wall the tile should be added to
     * @param tile the tile that should be added
     * @param row on which row it should be added
     * @param wallGridPane the GridPane used to show the wall
     */
    public void addTileToWall(Wall wall, Tile tile, int row, GridPane wallGridPane) {
        Rectangle tileToWall = findTileLocationInRow(row, tile, wallGridPane);
        if (tileToWall != null) {
            tileToWall.setOpacity(1.0);
            tileToWall.setStrokeWidth(3);
            int column = GridPane.getColumnIndex(tileToWall);
            wall.addTile(tile, row, column);
        }
    }

    /**
     * Finds a Rectangle in GridPane for a given tile in a row
     * @param rowNumber the number of the row for finding the tile
     * @param tile the tile of which location is needed
     * @param wallGridPane the GridPane associated with wall
     * @return the Rectangle associated with that tile
     */
    public Rectangle findTileLocationInRow(int rowNumber, Tile tile, GridPane wallGridPane) {
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
     * resets all the tiles back to original look
     * @param wallGridPane the GridPane on which the wall is shown
     */
    public void resetWallView(GridPane wallGridPane) {
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                node.setOpacity(0.5);
                ((Rectangle) node).setStrokeWidth(1);
            }
        }
    }

}
