package pppp.group14project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The Wall class represents the central game board in Azul where tiles are placed to score points.
 */
public class Team13Wall {
    private static List<List<TileType>> backgroundTiles;
    private List<List<Optional<TileType>>> placedTiles;

    /**
     * Initializes the background tiles of the wall.
     */
    static {
        backgroundTiles = new ArrayList<>(5);

        // Initialize a row of background tiles
        var row = new ArrayList<TileType>(5);
        row.add(TileType.BLUE);
        row.add(TileType.ORANGE);
        row.add(TileType.RED);
        row.add(TileType.BLACK);
        row.add(TileType.AQUITE);

        // Add rotated rows to create the background tiles
        for (int i = 1; i <= 5; i++) {
            backgroundTiles.add(new ArrayList<>(row));
            Collections.rotate(row, 1);
        }
    }

    /**
     * Constructs a new Wall instance with empty placed tiles.
     */
    public Team13Wall() {
        placedTiles = new ArrayList<>(5);
        Optional<TileType> emptyTitle = Optional.empty();

        // Fill the placed tiles with empty values
        for (int i = 1; i <= 5; i++) {
            placedTiles.add(new ArrayList<>(Collections.nCopies(5, emptyTitle)));
        }
    }

    /**
     * Returns the background tiles of the wall.
     *
     * @return The background tiles of the wall.
     */
    public static List<List<TileType>> getBackgroundTiles() {
        return backgroundTiles;
    }

    /**
     * Adds a tile to the specified row of the wall and calculates the score.
     *
     * @param tile The type of tile to be added.
     * @param row  The row number where the tile should be placed.
     * @return The score obtained by adding the tile to the wall.
     * @throws RuntimeException if the tile cannot be placed on the specified row.
     */
    public int addTile(TileType tile, int row) {
        var backgroundRow = backgroundTiles.get(row);
        var placedRow = placedTiles.get(row);

        int col = -1;
        for (int i = 0; i <= 4; i++) {
            if (backgroundRow.get(i) == tile && placedRow.get(i).isEmpty()) {
                placedRow.set(i, Optional.of(tile));
                col = i;
                break;
            }
        }

        if (col != -1) {
            int score = 0;

            // Check horizontally linked tiles
            int horizLinked = countHorizontallyLinkedTiles(row, col);

            // Check vertically linked tiles
            int vertLinked = countVerticallyLinkedTiles(row, col);

            if (horizLinked > 1) {
                score += horizLinked;
            }
            if (vertLinked > 1) {
                score += vertLinked;
            }
            if (horizLinked == 1 && vertLinked == 1) {
                score = 1;
            }

            return score;
        }
        throw new RuntimeException("The " + tile.toString() + " tile can't be placed on the row " + row + ", because it's already taken.");
    }

    /**
     * Counts the number of horizontally linked tiles for a given position.
     *
     * @param row The row number of the tile.
     * @param col The column number of the tile.
     * @return The count of horizontally linked tiles.
     */
    private int countHorizontallyLinkedTiles(int row, int col) {
        int count = 1;
        // Check tiles to the left
        for (int i = col - 1; i >= 0; i--) {
            if (placedTiles.get(row).get(i).isPresent()) {
                count++;
            } else {
                break;
            }
        }
        // Check tiles to the right
        for (int i = col + 1; i <= 4; i++) {
            if (placedTiles.get(row).get(i).isPresent()) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Counts the number of vertically linked tiles for a given position.
     *
     * @param row The row number of the tile.
     * @param col The column number of the tile.
     * @return The count of vertically linked tiles.
     */
    private int countVerticallyLinkedTiles(int row, int col) {
        int count = 1;
        // Check tiles above
        for (int i = row - 1; i >= 0; i--) {
            if (placedTiles.get(i).get(col).isPresent()) {
                count++;
            } else {
                break;
            }
        }
        // Check tiles below
        for (int i = row + 1; i <= 4; i++) {
            if (placedTiles.get(i).get(col).isPresent()) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Calculates the additional end game score based on completed lines and colors.
     *
     * @return The additional end game score.
     */
    public int calculateEndGameScore() {
        int endGameScore = 0;

        // Check complete horizontal lines
        int completeHorizontalLines = countCompleteHorizontalLines();
        endGameScore += completeHorizontalLines * 2;

        // Check complete vertical lines
        int completeVerticalLines = countCompleteVerticalLines();
        endGameScore += completeVerticalLines * 7;

        // Check colors with all 5 tiles placed
        int completeColors = countCompleteColors();
        endGameScore += completeColors * 10;

        return endGameScore;
    }

    /**
     * Counts the number of complete horizontal lines on the wall.
     *
     * @return The count of complete horizontal lines.
     */
    private int countCompleteHorizontalLines() {
        int count = 0;
        for (List<Optional<TileType>> row : placedTiles) {
            if (row.stream().allMatch(Optional::isPresent)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of complete vertical lines on the wall.
     *
     * @return The count of complete vertical lines.
     */
    private int countCompleteVerticalLines() {
        int count = 0;
        for (int col = 0; col < 5; col++) {
            boolean isComplete = true;
            for (var row : placedTiles) {
                if (row.get(col).isEmpty()) {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of colors with all 5 tiles placed on the wall.
     *
     * @return The count of colors with all 5 tiles placed.
     */
    private int countCompleteColors() {
        int count = 0;
        for (TileType color : TileType.values()) {
            boolean isCompleteColor = true;
            for (var row : placedTiles) {
                if (row.stream().noneMatch(tile -> tile.orElse(null) == color)) {
                    isCompleteColor = false;
                    break;
                }
            }
            if (isCompleteColor) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if there is at least one complete horizontal line on the wall.
     *
     * @return true if there is at least one complete horizontal line, false otherwise.
     */
    public boolean isHorizontalSomewhere() {
        for (var row : placedTiles) {
            if (row.stream().allMatch(Optional::isPresent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears all placed tiles on the wall.
     */
    public void clearTiles() {
        placedTiles = new ArrayList<>(5);
        Optional<TileType> emptyTitle = Optional.empty();
        for (int i = 1; i <= 5; i++) {
            placedTiles.add(new ArrayList<>(Collections.nCopies(5, emptyTitle)));
        }
    }

    /**
     * Checks if a specific tile type is present on the wall.
     *
     * @param toSearch The tile type to search for.
     * @return true if the tile type is present on the wall, false otherwise.
     */
    public boolean isPresent(TileType toSearch) {
        for (var row : placedTiles) {
            for (var tile : row) {
                if (tile.isPresent() && tile.get() == toSearch) {
                    return true;
                }
            }
        }

        return false;
    }
}


