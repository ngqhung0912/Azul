package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FactoryIntegrationTest extends ApplicationTest {

    private GameBoardController gameBoardController;

    private static FactoryController factoryController;

    private static Factory factory;

    private static Board board;

    private static Player player;

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
        game.addBoard(board);
        game.getFactoryList().clear();

        List<Factory> factories = game.getFactoryList();

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));

        //get game instances more factories
        factories.clear();
        factories.add(new Factory());

        gameBoard.load();

        gameBoardController = gameBoard.getController();

        factoryController = gameBoardController.getFactoryControllers().get(0);

        factory = factoryController.getFactory();

        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void beforeEach() {
        //if not cleared more tiles are added
        factory.empty();
    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(factoryController);
        assertNotNull(factory);
    }


    @Test
    void testMoveTilesToFactory() throws FullException {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.ORANGE);
        game.fillFactories(tileList);
        assertEquals(tileList.size(), factory.size());
        assertEquals(tileList, factoryController.getFactory().getTiles());
        factory.empty();
    }

    @Test
    void moveTooManyTilesToFactory() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLUE);

        assertThrows(FullException.class, () -> {
            game.getFactoryList().get(0).addTiles(tileList);
        });

    }

    @Test
    void removeTilesFromFactory() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.ORANGE);
        game.fillFactories(tileList);
        assertEquals(tileList.size(), factory.size());
        factoryController.getFactory().grabTiles(Tile.RED);
        assertEquals(0, factory.size());
        factory.empty();
    }

    @Test
    void notEnoughTiles(){
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        game.fillFactories(tileList);
        assertEquals(tileList.size(), factory.size());
        factoryController.getFactory().grabTiles(Tile.RED);
        assertEquals(0, factory.size());
        factory.empty();
    }
}

