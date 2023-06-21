package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;

import java.util.Collections;

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
}

