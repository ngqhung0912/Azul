package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import pppp.group14project.controller.*;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pppp.group14project.model.exceptions.WrongTileException;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.NodeQueryUtils.isVisible;

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

    private GridPane gameBoardPane;



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
        game.addBoard(board);

        List<Factory> factories = game.getFactoryList();

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));

        factories.clear();
        factories.add(new Factory());

        gameBoardPane = gameBoard.load();

        gameBoardController = gameBoard.getController();

        tableController = gameBoardController.getTableController();
        table = tableController.getTable();

        factoryController = gameBoardController.getFactoryControllers().get(0);

        factory = factoryController.getFactory();

        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        wallController = playerBoardController.getWallController();

        patternController = playerBoardController.getPatternController();

        floorController = playerBoardController.getFloorController();

        floor = floorController.getFloor();

        pattern = patternController.getPattern();


        stage.setScene(new Scene(root,1250, 700));
        stage.show();
        stage.toFront();
    }


    @AfterEach
    public void tearDown() throws Exception{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
//        game.resetGame();
        factory.empty();

    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(factoryController);
        assertNotNull(factory);
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
    void moveTilesFromTableToPattern() throws FullException, WrongTileException, InvalidPositionException {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tableController.addTilesToTable(tileList);
        assertEquals(tileList.size()+1, table.size());

        List<Tile> expectedList = new ArrayList<>();
        expectedList.add(Tile.STARTING);
        expectedList.add(Tile.BLUE);
        expectedList.add(Tile.BLUE);

        tableController.setSelectedTiles(Tile.BLUE);
        assertEquals(expectedList, table.grabTiles(Tile.BLUE).get(0));

        table.empty();
        assertEquals(0, table.size());
        tableController.addTilesToTable(tileList);

        playerBoardController.activate(Tile.BLUE, table);

        patternController.patternMoveTiles(table, Tile.BLUE, 1);

        assertTrue(patternController.getPattern().getPatternLines().get(1).isFull());


    }


}

