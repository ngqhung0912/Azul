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

    /**
     * Functions loops through the whole wall and creates list of tiles present
     *
     * @return list of tiles in the wall
     */
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

    /**
     * Adds tile to the wall on a given row x column position
     *
     * @param tile   tile to be added
     * @param row    row on which the tile should be added
     * @param column column in which the tile should be added
     */
    public void addTile(Tile tile, int row, int column) {
        Tile[] targetRow = this.wall[row];

        if (!isTileInRow(tile, row)) {
            targetRow[column] = tile;
            getScoreOfAddedTile(row, column);
        }
    }

    /**
     * Counts the number of tiles in a given row
     *
     * @param row row in which tiles should be counted
     * @return Number of tiles present in a row
     */
    public int countNonNullElementsInRow(Tile[] row) {
        int count = 0;

        for (Tile tile : row) {
            if (tile != null) {
                count++;
            }
        }

        return count;
    }

    /**
     * Check whether a given row is full
     *
     * @param row row to be checked
     * @return whether the row is full
     */
    public boolean isRowFull(Tile[] row) {
        return countNonNullElementsInRow(row) == 5;
    }

    /**
     * Checks whether a given tiles is in a row
     *
     * @param tile tile in question
     * @param row  row to be checked
     * @return whether the tile is in the row
     */
    public boolean isTileInRow(Tile tile, int row) {
        Tile[] targetRow = this.wall[row];
        for (Tile t : targetRow) {
            if (t == tile) {
                return true;
            }
        }

        return false;
    }

    /**
     * Calculates number of full rows in the wall
     *
     * @return number of full rows
     */
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

    /**
     * Calculates the number of full Columns in the wall
     *
     * @return number of full columns
     */
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

    /**
     * Calculates the score after each tile is added to the wall
     *
     * @param row row on which tile was added
     * @param col column on which tile was added
     */
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


    /**
     * Returns an array of tiles from given row
     *
     * @param rowNumber the row to be returned
     * @return the array of tiles
     */
    public Tile[] getRow(int rowNumber) {
        return this.wall[rowNumber];
    }

    /**
     * Empties the wall, sets all arrays to nulls
     */
    public void emptyWall() {
        for (int i = 0; i < wallSize; i++) {
            for (int j = 0; j < wallSize; j++) {
                wall[i][j] = null;
            }
        }
        this.wallScore = 0;
    }

}
