package pppp.group14project.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Wall {


    private ArrayList<ArrayList<Tile>> wall;

    @Getter
    public int wallScore;

    public Wall() {
        int wallSize = 5;
        this.wallScore = 0;
        this.wall = new ArrayList<>(wallSize);

        for (int i = 0; i < wallSize; i++) {
            ArrayList<Tile> row = new ArrayList<>(wallSize);
            for(int j=0; j<wallSize; j++){
                row.add(null);
            }
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

        if (!isTileInRow(tile, row)) {
            targetRow.set(column, tile);
            getScoreOfAddedTile(row, column);
        }
    }

    public int countNonNullElementsInRow(ArrayList<Tile> row) {
        int count = 0;

        for (Tile tile : row) {
            if (tile != null) {
                count++;
            }
        }

        return count;
    }

    public boolean isRowFull(ArrayList<Tile> row) {
        return countNonNullElementsInRow(row) == 5;
    }

    public boolean isTileInRow(Tile tile, int row) {
        ArrayList<Tile> targetRow = this.wall.get(row);
        for (Tile t : targetRow) {
            if (t == tile) { // Assuming Tile is an enum representing colors
                return true;
            }
        }

        return false;
    }

    public int getFullRows() {
        int fullRows = 0;
        for (ArrayList<Tile> row : wall) {
            boolean isRowFull = true;
            for (Tile tile : row) {
                if (tile == null) {
                    isRowFull = false;
                    break;
                }
            }
            if (isRowFull) {
                fullRows++;
            }
        }
        return fullRows;
    }

    public int getFullCols() {
        int fullCols = 0;

        for (int col = 0; col < wall.size(); col++) {
            boolean isColumnFull = true;
            for (ArrayList<Tile> row : wall) {
                Tile tile = row.get(col);
                if (tile == null) {
                    isColumnFull = false;
                    break;
                }
            }
            if (isColumnFull) {
                fullCols++;
            }
        }
        return fullCols;
    }

    public void getScoreOfAddedTile(int row, int col) {

        // Check right side
        if (col < wall.get(row).size() - 1 && wall.get(row).get(col + 1) != null) {
            this.wallScore++;
        }
        // Left side
        if (col > 0 && wall.get(row).get(col - 1) != null) {
            this.wallScore++;
        }

        // Bottom
        if (row < wall.size() - 1 && wall.get(row + 1).get(col) != null) {
            this.wallScore++;
        }

        // Top
        if (row > 0 && wall.get(row - 1).get(col) != null) {
            this.wallScore++;
        }
        // +1 to the score for just placing the tile
        this.wallScore++;
    }


    public ArrayList<Tile> getRow(int rowNumber) {
        return this.wall.get(rowNumber);
    }

    public void emptyWall() {
        this.wall.clear();
        this.wallScore = 0;
    }

}

//get all tiles in the wall as a list and then check it size