package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

class ControllerTest extends ApplicationTest {

    private GameBoardController gameBoardController;

    private static TableController tableController;

    private static Table table;

    private static WallController wallController;

    private static Wall wall;

    private static FactoriesController factoriesController;

    private static Factory factory;

    private static FloorController floorController;

    private static Floor floor;

    private static PatternController patternController;

    private static Pattern pattern;

    private static Board board;

    private static Player player;

    private static PlayerBoardController playerBoardController;


    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        player = new Player("test");

        Game game = Game.getInstance();
        board = new Board(player);

        game.addBoard(board);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();

        gameBoardController = gameBoard.getController();

        tableController = gameBoardController.getTableController();
        table = tableController.getTable();

        factoriesController = gameBoardController.getFactoriesController();
        factory = factoriesController.getFactory();


        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        wallController = playerBoardController.getWallController();
        wall = wallController.getWall();

        patternController = playerBoardController.getPatternController();
        pattern = patternController.getPattern();

        floorController = playerBoardController.getFloorController();
        floor = floorController.getFloor();



        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(table);
        assertNotNull(factoriesController);
        //TODO fix the way factories are passed
//        assertNotNull(factory);
        assertNotNull(wallController);
        assertNotNull(wall);
        assertNotNull(patternController);
        assertNotNull(pattern);
        assertNotNull(floorController);
        assertNotNull(floor);

    }


    @Test
    void tableRemoveAllAddedTiles() throws Exception {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);

        tableController.addTilesToTable(tileList);
        int expectedCount = 5;
        assertEquals(expectedCount, table.size());
        tableController.grabTilesFromTable(Tile.BLUE);
        assertEquals(0, table.size());
    }

    @Test
    void tableCountAddedTiles() throws Exception {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tileList.add(Tile.WHITE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);

        tableController.addTilesToTable(tileList);
        int expectedCount = 7;
        assertEquals(expectedCount, table.size());
    }

    @Test
    void wallReset(){
        wallController.addTileToWall(Tile.ORANGE, 0);
        wallController.addTileToWall(Tile.ORANGE, 1);
        wallController.addTileToWall(Tile.ORANGE, 2);
        assertEquals(3, wall.getTilesInWall().size());
        wallController.resetWallView();
        assertEquals(0, wall.getTilesInWall().size());
    }

    @Test
    void wallAddTiles(){
        wallController.addTileToWall(Tile.RED, 2);
        assertEquals(1, wall.getTilesInWall().size());
        //No two tiles in the same row
        wallController.addTileToWall(Tile.RED, 2);
        assertEquals(1, wall.getTilesInWall().size());
        wallController.addTileToWall(Tile.BLUE, 2);
        assertEquals(2, wall.getTilesInWall().size());
        assertEquals(0, wall.countNonNullElementsInRow(wall.getRow(0)));
        assertEquals(2, wall.countNonNullElementsInRow(wall.getRow(2)));
        wallController.resetWallView();
    }

    @Test
    void wallAddTilesInCorrectSpot(){
        wallController.addTileToWall(Tile.BLACK, 3);
        assertEquals(Tile.BLACK, wall.getRow(3)[2]);
        wallController.resetWallView();
    }



}

