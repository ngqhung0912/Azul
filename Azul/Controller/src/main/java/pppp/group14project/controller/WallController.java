package pppp.group14project.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;

public class WallController {



    public void addTileToWall(Wall wall, Tile tile, int row, GridPane wallGridPane) {
        Rectangle tileToWall = findTileLocationInRow(row, tile, wallGridPane);
        if (tileToWall != null) {
            tileToWall.setOpacity(1.0);
            tileToWall.setHeight(70);
            tileToWall.setWidth(70);
            int column = GridPane.getColumnIndex(tileToWall);
            wall.addTile(tile, row, column);
        }
    }


    public Rectangle findTileLocationInRow(int rowNumber, Tile tile, GridPane wallGridPane) {
        String color = tile.toString();
        Rectangle tileToWall = null;
        for (Node node : wallGridPane.getChildren()) {
            double opacityNoTile = 0.3;
            if (node.getId().equals(color)
                    && GridPane.getRowIndex(node) == rowNumber
                    && node.getOpacity() == opacityNoTile) {
                tileToWall = (Rectangle) node;
            }
        }
        return tileToWall;
    }

    public void resetWallView(GridPane wallGridPane){
        for(Node node : wallGridPane.getChildren()){
            if (node instanceof Rectangle){
                node.setOpacity(0.3);
                ((Rectangle) node).setWidth(50);
                ((Rectangle) node).setHeight(50);
            }
        }
    }

}
