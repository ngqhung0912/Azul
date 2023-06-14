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
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

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

    private static FactoryController factoryController;

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

//        factoryController = gameBoardController.getfactoryController();
//        factory = factoryController.getFactory();


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
    void testInitialization() {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(table);
//        assertNotNull(factoryController);
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
    void tableRemoveAllAddedTiles()  {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        //TODO remove the tiles from the initialization
        try {
            tableController.addTilesToTable(tileList);
        } catch (Exception e) {
            fail("Should not throw FullException");
        }
        assertEquals(tileList.size(), table.size());
        try {
            tableController.grabTilesFromTable(Tile.BLUE);
        } catch (Exception e) {
            fail("Should not throw FullException");
        }
        assertEquals(0, table.size());
    }



    @Test
    void tableCountAddedTiles() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tileList.add(Tile.WHITE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);

        try {
            tableController.addTilesToTable(tileList);
        } catch (Exception e) {
            fail("Should not throw FullException");
        }
        assertEquals(tileList.size(), table.size());
    }


    @Test
    void wallReset()  {
        try {
            wallController.addTileToWall(Tile.ORANGE, 0);
            wallController.addTileToWall(Tile.ORANGE, 1);
            wallController.addTileToWall(Tile.ORANGE, 2);
            assertEquals(3, wall.getTilesInWall().size());
            wallController.resetWallView();
            assertEquals(0, wall.getTilesInWall().size());
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
    }

    @Test
    void wallAddTiles() {
        try {
            wallController.addTileToWall(Tile.RED, 2);
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
        assertEquals(1, wall.getTilesInWall().size());
        //No two tiles in the same row
        try {
            wallController.addTileToWall(Tile.RED, 2);
            fail("Expect FullException");
        } catch (FullException ignored) {}

        assertEquals(1, wall.getTilesInWall().size());

        try {
            wallController.addTileToWall(Tile.BLUE, 2);
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
        assertEquals(2, wall.getTilesInWall().size());
        assertEquals(0, wall.countNonNullElementsInRow(wall.getRow(0)));
        assertEquals(2, wall.countNonNullElementsInRow(wall.getRow(2)));
        wallController.resetWallView();
    }

    @Test
    void wallAddTilesInCorrectSpot() {
        try {
            wallController.addTileToWall(Tile.BLACK, 3);
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
        assertEquals(Tile.BLACK, wall.getRow(3).get(1));
        wallController.resetWallView();
    }


    @Test
    void testAddTilesToFloor() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tileList.add(Tile.WHITE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);
        floorController.addTilesToFloor(tileList);
        assertEquals(tileList.size(), floor.getTiles().size());

        List<Tile> additionalTileList = new ArrayList<>();
        additionalTileList.add(Tile.ORANGE);
        additionalTileList.add(Tile.BLACK);

        floorController.addTilesToFloor(additionalTileList);
        assertEquals(7, floor.getTiles().size());

        assertEquals(-14, floor.getScore());
        // TODO test whether the excess tile end up in the box lid.

        floor.emptyFloor();
        assertEquals(0, floor.getTiles().size());
    }

    @Test
    void testAddCorrectTilesToPattern() {
        List<Tile> tileList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            tileList.add(Tile.BLUE);
            tileList.add(Tile.BLUE);

            try {
                pattern.addTiles(i, tileList);
            } catch (WrongTileException e) {
                fail("Should not throw WrongTileException");
            }
            assertEquals(tileList.size(), pattern.getPatternLines().get(i).numberOfFullSpaces());
            assertEquals(0, pattern.getPatternLines().get(i).numberOfFreeSpaces());
            assertTrue(pattern.getPatternLines().get(i).isFull());
            assertFalse(pattern.getPatternLines().get(i).isEmpty());

            pattern.getPatternLines().get(i).empty();


            assertFalse(pattern.getPatternLines().get(i).isFull());
            assertTrue(pattern.getPatternLines().get(i).isEmpty());


            assertEquals(tileList.size(), pattern.getPatternLines().get(i).numberOfFreeSpaces());
            assertEquals(0, pattern.getPatternLines().get(i).numberOfFullSpaces());




        }
    }

    @Test
    void testAddWrongTilesToPattern() {

    }























}

