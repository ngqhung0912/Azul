package pppp.group14project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Board;
import pppp.group14project.model.Factory;
import pppp.group14project.model.PatternLine;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerBoardController implements Initializable, Mediator {

    /**
     * FXML for updating the View
     */
    @FXML
    public Label playerName;
    @FXML
    private GridPane playerBoardGrid;
    @FXML
    public Rectangle boardBackground;

    /**
     * Player board model
     */
    @Setter
    @Getter
    private Board board;

    /**
     * References to other controllers
     */
    @Setter
    @Getter
    private PatternController patternController;
    @Setter
    @Getter
    private FloorController floorController;
    @Setter
    @Getter
    private WallController wallController;
    @Setter
    @Getter
    private ScoreController scoreController;
    @Setter
    @Getter
    private GameBoardController gameBoardController;

    public void setPlayerName(String playerName) {
        this.playerName.setText(playerName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader floorLoader = new FXMLLoader(getClass().getResource("/player-floor-view.fxml"));
            FXMLLoader patternLoader = new FXMLLoader(getClass().getResource("/player-pattern-view.fxml"));
            FXMLLoader scoreLoader = new FXMLLoader(getClass().getResource("/board-score-view.fxml"));
            FXMLLoader wallLoader = new FXMLLoader(getClass().getResource("/player-wall-view.fxml"));
            StackPane playerPattern = patternLoader.load();
            GridPane playerFloor = floorLoader.load();
            GridPane playerScore = scoreLoader.load();
            GridPane playerWall = wallLoader.load();
            playerScore.setMouseTransparent(true);

            playerBoardGrid.add(playerPattern, 1, 1);
            playerBoardGrid.add(playerFloor, 1, 2);
            playerBoardGrid.add(playerScore, 3, 2);
            playerBoardGrid.add(playerWall, 3, 1);

            patternController = patternLoader.getController();
            floorController = floorLoader.getController();
            patternController.setPlayerBoardController(this);
            floorController.setPlayerBoardController(this);
            wallController = wallLoader.getController();
            wallController.setPlayerBoardController(this);
            scoreController = scoreLoader.getController();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the models, once all models of its parent models have loaded
     */
    public void postInitialize() {

        floorController.setPlayerBoardController(this);
        floorController.setFloor(board.getFloor());
        floorController.postInitialize();

        patternController.setPlayerBoardController(this);
        patternController.setPattern(board.getPattern());
        patternController.postInitialize();

        wallController.setPlayerBoardController(this);
        wallController.setWall(board.getWall());
        wallController.postInitialize();

        scoreController.setBoard(board);
        scoreController.postInitialize();
    }

    /**
     * Activates this PlayerBoard for interactivity, and highlights possible spaces for some passed tile
     *
     * @param tile
     * @param factory
     */
    public void activate(Tile tile, Factory factory) throws InvalidPositionException {
        boolean hasPlacedAutomatically = patternController.highlightPossibleSpaces(tile, factory);
        if (hasPlacedAutomatically) return;
        floorController.highlightFloor(tile, factory);
    }

    public void deactivate() {
        floorController.unhighlightEntireFloor();
        patternController.unhighlightAllSpaces();
        gameBoardController.finishPlayerTurn();
    }

    public List<Tile> moveTileFromPatternToWall() {
        List<Tile> returnTiles = new ArrayList<>();
        List<PatternLine> patternLines = patternController.getPattern().getPatternLines();

        try {
            for (int i = 0; i < patternLines.size(); i++) {
                PatternLine patternLine = patternLines.get(i);
                if (patternLine.isFull()) {
                    List<Tile> tilesToMove = new ArrayList<>(patternLine.getSpaces());
                    Tile wallTile = tilesToMove.remove(0);
                    moveTileToWall(wallTile, i);
                    returnTiles.addAll(tilesToMove);
                    // Move remaining tiles to discardedTiles in TileContainer
                    patternLine.empty();
                }
            }
        } catch (FullException | WrongTileException ignored) {
            throw new RuntimeException(ignored);
        }

        return returnTiles;
    }

    /**
     * Concrete Mediator implementation of moving tiles between different GameBoard components
     */

    @Override
    public void moveTileToWall(Tile tile, int rowIndex) throws WrongTileException, FullException {
        wallController.addTileToWall(tile, rowIndex);
    }

    @Override
    public void moveTilesToFloor(List<Tile> tiles) {
        floorController.addTilesToFloor(tiles);
    }

    @Override
    public void moveTilesToTable(List<Tile> tiles) {
        gameBoardController.moveTilesToTable(tiles);
    }

    @Override
    public void moveTilesToTileContainer(List<Tile> tile) {
    }

    @Override
    public void moveTileToTileContainer(Tile tile) {
        gameBoardController.moveTileToTileContainer(tile);
    }

    @Override
    public void updateScore() {
        board.updateScore();
    }

    @Override
    public List<Tile> removeTilesFromFloor() {
        return floorController.getFloor().emptyFloor();
    }


    public boolean wallContainsTile(Tile tile, int row) {
        return wallController.wallContainsTile(tile, row);
    }
}
