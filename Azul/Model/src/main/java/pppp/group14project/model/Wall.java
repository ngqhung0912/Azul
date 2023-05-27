package pppp.group14project.model;

import java.util.ArrayList;
import java.util.List;

public class Wall {


    private  ArrayList<ArrayList<Tile>> wall;

    public Wall() {
        int wallSize = 5;
        this.wall = new ArrayList<>(wallSize);

        for (int i = 0; i < wallSize; i++) {
            ArrayList<Tile> row = new ArrayList<>(wallSize);
            wall.add(row);
        }
    }

    public List<Tile> getTilesInWall() {
        List<Tile> tilesInWall = new ArrayList<>();

        for (ArrayList<Tile> row : wall) {
            for (Tile tile : row) {
                if (tile != null) {
                    tilesInWall.add(tile);
                }
            }
        }

        return tilesInWall;
    }

    public void addTile(Tile tile, int row, int column) {
            ArrayList<Tile> targetRow = this.wall.get(row);

            while (targetRow.size() <= column) {
                targetRow.add(null);
            }
            targetRow.set(column, tile);
    }

    public boolean isRowFull(ArrayList<Tile> row) {
        return row.size() == 5;
    }

    public ArrayList<Tile> getRow(int rowNumber) {
        return this.wall.get(rowNumber);
    }

    public void emptyWall() {
        this.wall.clear();
    }

}

//get all tiles in the wall as a list and then check it size