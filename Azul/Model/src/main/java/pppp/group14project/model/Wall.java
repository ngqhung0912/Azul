package pppp.group14project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.*;

public class Wall {

    private static final List<List<Tile>> TILE_COLORS = Arrays.asList(
            Arrays.asList(Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE),
            Arrays.asList(Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK),
            Arrays.asList(Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED),
            Arrays.asList(Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE),
            Arrays.asList(Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE)
    );


    @Getter
    private List<ObservableList<Tile>> wall;

    protected static final int WALL_SIZE = 5;


    @Getter
    protected int wallScore;

    public Wall() {
        this.wallScore = 0;
        this.wall = new ArrayList<>();
        for (int i = 0; i < WALL_SIZE; i++) {
            ObservableList<Tile> row = FXCollections.observableArrayList();
            for (int j = 0; j < WALL_SIZE; j++) {
                row.add(null);
            }
            wall.add(row);
        }
    }

    protected static int getTileColorColumn(Tile t, int row) throws WrongTileException {
        for (int i = 0; i < TILE_COLORS.get(row).size(); i++) {
            if (TILE_COLORS.get(row).get(i) == t) return i;
        }
        throw new WrongTileException("Tile color not found in grid");
    }

    public static Tile getTileColor(int row, int column) {
        return TILE_COLORS.get(row).get(column);
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
    public void addTile(Tile tile, int row) throws FullException, WrongTileException {
        if (isTileInRow(tile, row)) throw new FullException();
        int col = getTileColorColumn(tile, row);
        addTile(tile, row, col);
        updateWallScore(row, col);

    }


    /**
     * Adds tile to the wall on a given row x column position
     *
     * @param tile   tile to be added
     * @param row    row on which the tile should be added
     * @param column column in which the tile should be added
     */
    private void addTile(Tile tile, int row, int column) {
        ObservableList<Tile> targetRow = this.wall.get(row);
        targetRow.set(column, tile);
    }

    /**
     * Counts the number of tiles in a given row
     *
     * @param rowNumber row in which tiles should be counted
     * @return Number of tiles present in a row
     */
    public int countTilesInRow(int rowNumber) {
        return wall.get(rowNumber).stream().filter(Objects::nonNull).toArray().length;
    }

    /**
     * Check whether a given row is full
     *
     * @param rowNumber row to be checked
     * @return whether the row is full
     */
    private boolean isRowFull(int rowNumber) {
        return countTilesInRow(rowNumber) == 5;
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
        for (int i = 0; i < WALL_SIZE; i++) {
            if (isRowFull(i)) {
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
    private int getFullCols() {
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

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < wall.size() && col >= 0 && col < wall.get(row).size();
    }

    private boolean cellContainsTile(int row, int col) {
        return wall.get(row).get(col) != null;
    }

    /**
     * Counts the number of tiles in a given direction from a given cell.
     * @return number of neighboring tiles in the given direction
     */
    private int countNeighboringTiles(int row, int col, String direction) {
        int count = 0;
        while (isValidCell(row, col) && cellContainsTile(row, col)) {
            count++;
            switch (direction) {
                case "right" -> col++;
                case "left" -> col--;
                case "top" -> row++;
                case "bottom" -> row--;
            }
        }
        return count;
    }

    /**
     * Update wall score after every move
     */

    private void updateWallScore(int row, int col) {
        assert (row >= 0 && row < wall.size());
        assert (col >= 0 && col < wall.get(row).size());


        int horizontalNeighboringTiles = countNeighboringTiles(row, col + 1, "right") +  // right
                countNeighboringTiles(row, col - 1, "left");  // left
        if (horizontalNeighboringTiles > 0) {
            wallScore += horizontalNeighboringTiles + 1;
        }

        int verticalNeighboringTiles = countNeighboringTiles(row + 1, col, "top") +  // top
                countNeighboringTiles(row - 1 , col, "bottom");  // bottom
        if (verticalNeighboringTiles > 0) {
            wallScore += verticalNeighboringTiles + 1;
        }

        if (horizontalNeighboringTiles == 0 && verticalNeighboringTiles == 0) {
            wallScore += 1;
        }

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
        for (int i = 0; i < WALL_SIZE; i++) {
            for (int j = 0; j < WALL_SIZE; j++) {
                wall.get(i).set(j, null);
            }
        }
        this.wallScore = 0;
    }

    /**
     * Note that the UpdateScoreAtEndGame function should only be called once,
     * but tests call it multiple times for convenience.
     */
    public void updateScoreAtEndGame() {
        this.wallScore += getAdditionalScoreOfCompleteRows()  +
                getAdditionalScoreOfCompleteCols() +
                getAdditionalScoreOfCompleteColors();
    }

    /**
     * Calculates the additional score (2) for each complete row.
     * @return additional score
     */

    private int getAdditionalScoreOfCompleteRows() {
        return this.getFullRows() * 2;

    }
    /**
     * Calculates the additional score (7) for each complete column.
     * @return additional score
     */

    private int getAdditionalScoreOfCompleteCols() {
        return this.getFullCols() * 7;

    }
    /**
     * Calculates the additional score (10) for each complete colors.
     * @return the additional score
     */

    private int getAdditionalScoreOfCompleteColors() {
        return this.getFullColors() * 10;
    }

    private int getFullColors() {
        int fullColorsCount = 0;
        for (int i = 0; i < WALL_SIZE; i++) {
                Tile tile = TILE_COLORS.get(0).get(i);
                int counter = 0;
                for (int j = 0; j < WALL_SIZE; j++) {
                        if (isTileInRow(tile, j)) {
                            counter++;
                        }
                    if (counter == 5) {
                            fullColorsCount++;
                        }
                }
        }
        return fullColorsCount;
    }

}
