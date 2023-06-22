package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameIntegrationTest extends ApplicationTest {

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

    private static Game game;

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        player = new Player("test");

        game = Game.getInstance();
        board = new Board(player);
        factory = new Factory();
        floor = board.getFloor();

        game.getFactoryList().add(factory);
        game.addBoard(board);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();

        gameBoardController = gameBoard.getController();

        tableController = gameBoardController.getTableController();
        table = tableController.getTable();

//        factoryController = gameBoardController.getfactoryController();


        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        wallController = playerBoardController.getWallController();

        patternController = playerBoardController.getPatternController();

        floorController = playerBoardController.getFloorController();



        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
//        assertNotNull(factoryController);
        //TODO fix the way factories are passed
//        assertNotNull(factory);
        assertNotNull(wallController);
        assertNotNull(patternController);
        assertNotNull(floorController);

    }

    @Test
    void testMoveTileFromFloorToContainer() {
        for (int i = 0; i < 7; i++) {
            playerBoardController.moveTilesToFloor(Collections.singletonList(Tile.BLUE));
        }
        assertEquals(7, playerBoardController.getFloorController().getFloor().getTiles().size());
        assertEquals(0, gameBoardController.getGame().getTilecontainer().getDiscardedTiles().size() );

        for (int i = 0; i < 7; i++) {
            playerBoardController.moveTilesToFloor(Collections.singletonList(Tile.BLUE));
        }
        assertEquals(7, gameBoardController.getGame().getTilecontainer().getDiscardedTiles().size() );
    }

    @Test
    void moveTilesFromFactoryToFloor() throws FullException {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BLUE);
        tiles.add(Tile.RED);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.WHITE);
        factory.getTiles().clear();
        factory.addTiles(tiles);

        Button button = new Button();
        button.getStyleClass().add("tile-option");

        floorController.moveTilesToFloorFromFactory(Tile.BLUE, factory, button);

        assertFalse(button.getStyleClass().contains("tile-option"));
        assertTrue(floor.getTiles().contains(Tile.BLUE));
        assertFalse(floor.getTiles().contains(Tile.RED));
        assertFalse(floor.getTiles().contains(Tile.WHITE));
        assertEquals(2, floor.getTiles().size());
        assertTrue(table.getTiles().contains(Tile.RED));
        assertTrue(table.getTiles().contains(Tile.WHITE));
        assertFalse(table.getTiles().contains(Tile.BLUE));

        floor.getTiles().clear();
    }
}

