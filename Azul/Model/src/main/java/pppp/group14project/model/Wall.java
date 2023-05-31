package pppp.group14project.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Wall {


    private Tile[][] wall;
    private int wallSize;

    @Getter
    public int wallScore;

    public Wall() {
        this.wallSize = 5;
        this.wallScore = 0;
        this.wall = new Tile[wallSize][wallSize];
    }

    public List<Tile> getTilesInWall() {
        List<Tile> tilesInWall = new ArrayList<>();

        for (Tile[] row : wall) {
            for (Tile tile : row) {
                if (tile != null) {
                    tilesInWall.add(tile);
                }
            }
        }

        return tilesInWall;
    }

    public void addTile(Tile tile, int row, int column) {
        Tile[] targetRow = this.wall[row];

        if (!isTileInRow(tile, row)) {
            targetRow[column] = tile;
            getScoreOfAddedTile(row, column);
        }
    }

    public int countNonNullElementsInRow(Tile[] row) {
        int count = 0;

        for (Tile tile : row) {
            if (tile != null) {
                count++;
            }
        }

        return count;
    }

    public boolean isRowFull(Tile[] row) {
        return countNonNullElementsInRow(row) == 5;
    }

    public boolean isTileInRow(Tile tile, int row) {
        Tile[] targetRow = this.wall[row];
        for (Tile t : targetRow) {
            if (t == tile) {
                return true;
            }
        }

        return false;
    }

    public int getFullRows() {
        int fullRows = 0;
        for (Tile[] row : wall) {
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

        for (int col = 0; col < wall.length; col++) {
            boolean isColumnFull = true;
            for (Tile[] row : wall) {
                Tile tile = row[col];
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
        if (col < wall[row].length - 1 && wall[row][col + 1] != null) {
            this.wallScore++;
        }
        // Left side
        if (col > 0 && wall[row][col - 1] != null) {
            this.wallScore++;
        }

        // Bottom
        if (row < wall.length - 1 && wall[row + 1][col] != null) {
            this.wallScore++;
        }

        // Top
        if (row > 0 && wall[row - 1][col] != null) {
            this.wallScore++;
        }
        // +1 to the score for just placing the tile
        this.wallScore++;
    }


    public Tile[] getRow(int rowNumber) {
        return this.wall[rowNumber];
    }

    public void emptyWall() {
        for (int i = 0; i < wallSize; i++) {
            for (int j = 0; j < wallSize; j++) {
                wall[i][j] = null;
            }
        }
        this.wallScore = 0;
    }

}
