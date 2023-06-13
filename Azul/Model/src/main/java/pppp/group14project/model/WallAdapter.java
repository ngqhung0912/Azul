package pppp.group14project.model;

import java.util.*;

public class WallAdapter extends Wall {

    private Team13Wall team13Wall;

    private final Map<Tile, TileType> tileTypeToTileMap;

    private final Map<TileType, Tile> tileToTileTypeMap;


    public WallAdapter(Team13Wall team13Wall) {
        this.team13Wall = team13Wall;
        tileTypeToTileMap = new HashMap<>(5);
        tileTypeToTileMap.put(Tile.BLACK, TileType.BLACK);
        tileTypeToTileMap.put(Tile.BLUE, TileType.BLUE);
        tileTypeToTileMap.put(Tile.WHITE, TileType.AQUITE);
        tileTypeToTileMap.put(Tile.ORANGE, TileType.ORANGE);
        tileTypeToTileMap.put(Tile.RED, TileType.RED);


        tileToTileTypeMap = new HashMap<>(5);
        tileToTileTypeMap.put(TileType.BLACK, Tile.BLACK);
        tileToTileTypeMap.put(TileType.BLUE, Tile.BLUE);
        tileToTileTypeMap.put(TileType.AQUITE, Tile.WHITE);
        tileToTileTypeMap.put(TileType.ORANGE, Tile.ORANGE);
        tileToTileTypeMap.put(TileType.RED, Tile.RED);
    }

    /**
     * Add tile to Team 13's wall. Count score using our counter.
     * This already account for horizontally linked and vertically linked tiles.
     * Throw a RuntimeException if the tile cannot be added. GOD KNOWS WHY.
     *
     * @param tile   tile to be added
     * @param row    row on which the tile should be added
     * @param column column in which the tile should be added
     */
    @Override
    public void addTile(Tile tile, int row, int column) {
        try {
            int score = team13Wall.addTile(tileTypeToTileMap.get(tile), row);
            this.wallScore += score;
        } catch (RuntimeException ignored) {}
    }

    /**
     * Get the tiles in team 13's wall.
     * @return list of tiles in the wall.
     */
    @Override
    public List<Tile> getTilesInWall() {
        List<Tile> tilesInWall = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                Optional<TileType> tileType = team13Wall.getTile(i, j);
                if (tileType.isPresent()) {
                    Tile tile = tileToTileTypeMap.get(tileType.get());
                    tilesInWall.add(tile);
                }
            }
        }
        return tilesInWall;
    }

    @Override
    public int countTilesInRow(Tile[] row) {
        int count = 0;
        return count;
    }


    @Override
    public void emptyWall() {
        team13Wall.clearTiles();
    }








}
