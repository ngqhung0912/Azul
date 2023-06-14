package pppp.group14project.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wall {

    private static List<List<Tile>> tileColors = Arrays.asList(
            Arrays.asList(Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE),
            Arrays.asList(Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK),
            Arrays.asList(Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED),
            Arrays.asList(Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE),
            Arrays.asList(Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE)
    );

    public static int getTileColorColumn(Tile t, int row) throws FullException {
        for (int i = 0; i < tileColors.get(row).size(); i++) {
            if (tileColors.get(row).get(i) == t) return i;
        }
        throw new FullException("Tile color not found in grid");
    }

    public static Tile getTileColor(int row, int column) {
        return tileColors.get(row).get(column);
    }

    @Getter
    private List<ObservableList<Tile>> wall;
    private final int WALLSIZE = 5;


    @Getter
    public int wallScore;

    public Wall() {
        this.wallScore = 0;
        this.wall = new ArrayList<ObservableList<Tile>>();
        for (int i = 0; i < WALLSIZE; i++) {
            ObservableList<Tile> row = FXCollections.observableArrayList();
            for (int j = 0; j < WALLSIZE; j++) {
                row.add(null);
            }
            wall.add(row);
        }
    }

    /**
     * Functions loops through the whole wall and creates list of tiles present
     *
     * @return list of tiles in the wall
     */
    public List<Tile> getTilesInWall() {
        List<Tile> tilesInWall = new ArrayList<>();
        for (ObservableList<Tile> row : wall) {
            for (Tile tile : row) {
                if (tile != null) {
                    tilesInWall.add(tile);
                }
            }
        }
        return tilesInWall;
    }

    /**
     * Adds tile to the wall using a given row
     *
     * @param tile   tile to be added
     * @param row    row on which the tile should be added
     */
    public void addTile(Tile tile, int row) throws FullException {

        if (isTileInRow(tile, row)) throw new FullException();
        addTile(tile, row, Wall.getTileColorColumn(tile, row));

    }


    /**
     * Adds tile to the wall on a given row x column position
     *
     * @param tile   tile to be added
     * @param row    row on which the tile should be added
     * @param column column in which the tile should be added
     */
    public void addTile(Tile tile, int row, int column) {
        ObservableList<Tile> targetRow = this.wall.get(row);

        if (!isTileInRow(tile, row)) {
            targetRow.set(column, tile);
            getScoreOfAddedTile(row, column);
        }
    }

    /**
     * Counts the number of tiles in a given row
     * TODO then while the method is not named accordingly, i.e.: CountTilesInRow?
     * @param row row in which tiles should be counted
     * @return Number of tiles present in a row
     */
    public int countNonNullElementsInRow(List<Tile> row) {
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
    public boolean isRowFull(List<Tile> row) {
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
        ObservableList<Tile> targetRow = this.wall.get(row);
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
        for (ObservableList<Tile> row : wall) {
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

        for (int col = 0; col < wall.size(); col++) {
            boolean isColumnFull = true;
            for (ObservableList<Tile> row : wall) {
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

    /**
     * Calculates the score after each tile is added to the wall
     * TODO: this function is more like: updateScoreAfterTileAdded.
     * @param row row on which tile was added
     * @param col column on which tile was added
     */
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


    /**
     * Returns an array of tiles from given row
     *
     * @param rowNumber the row to be returned
     * @return the array of tiles
     */
    public List<Tile> getRow(int rowNumber) {
        return this.wall.get(rowNumber);
    }

    /**
     * Empties the wall, sets all arrays to nulls
     */
    public void emptyWall() {
        for (int i = 0; i < WALLSIZE; i++) {
            for (int j = 0; j < WALLSIZE; j++) {
                wall.get(i).set(j, null);
            }
        }
        this.wallScore = 0;
    }

}
