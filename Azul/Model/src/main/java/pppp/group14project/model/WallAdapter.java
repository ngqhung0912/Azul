package pppp.group14project.model;

import pppp.group14project.model.exceptions.FullException;

import java.util.*;

public class WallAdapter extends Wall {

    private final Team13Wall team13Wall;

    private final Map<Tile, TileType> tileTypeToTileMap;

    private final Map<TileType, Tile> tileToTileTypeMap;


    public WallAdapter() {
        this.team13Wall = new Team13Wall();
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
     */
    @Override
    public void addTile(Tile tile, int row) throws FullException {
        try {
            super.wallScore += team13Wall.addTile(tileTypeToTileMap.get(tile), row);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("tile can't be placed on the row")){
                throw new FullException("Tile cannot be added to wall");
            } else {
                System.out.println("not catched");
                throw e;
            }
        }
    }

    /**
     * Get the tiles in team 13's wall.
     * @return list of tiles in the wall.
     */
    @Override
    public List<Tile> getTilesInWall() {
        List<Tile> tilesInWall = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
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
    public int countTilesInRow(int rowNumber) {
        return this.getRow(rowNumber).stream().filter(Objects::nonNull).toArray().length;
    }


    @Override
    public void emptyWall() {
        team13Wall.clearTiles();
    }


    @Override
    public List<Tile> getRow(int rowNumber) {
        List<Tile> tilesInRow = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TileType tileType = team13Wall.getTile(rowNumber, i).orElse(null);
            if (tileType == null) {
                tilesInRow.add(null);
            } else {
                Tile tile = tileToTileTypeMap.get(tileType);
                tilesInRow.add(tile);
            }
        }
        return tilesInRow;
    }

    @Override
    public void updateScoreAtEndGame() {
        super.wallScore += team13Wall.calculateEndGameScore();
    }











}
