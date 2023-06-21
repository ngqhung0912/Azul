package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FloorIntegrationTest extends ApplicationTest {
    private static GameBoardController gameBoardController;

    private static FloorController floorController;

    private static Floor floor;

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
        board = new Board(player);
        Game game = Game.getInstance();
        game.addBoard(board);


        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();

        gameBoardController = gameBoard.getController();


        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);


        floorController = playerBoardController.getFloorController();
        floor = floorController.getFloor();



        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }


    @Test
    void testInitialization() {
        assertNotNull(gameBoardController);
        assertNotNull(floorController);
        assertNotNull(floor);

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
        tileList.add(Tile.BLACK);
        floorController.addTilesToFloor(tileList);
        assertEquals(tileList.size(), floor.getTiles().size());

        List<Tile> additionalTileList = new ArrayList<>();
        additionalTileList.add(Tile.ORANGE);
        additionalTileList.add(Tile.BLACK);

        floorController.addTilesToFloor(additionalTileList);
        assertEquals(7, floor.getTiles().size());
        assertEquals(-14, floor.getFloorScore());

        floor.emptyFloor();
        assertEquals(0, floor.getTiles().size());
    }



}
