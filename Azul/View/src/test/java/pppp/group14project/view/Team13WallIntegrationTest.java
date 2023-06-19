package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.GameBoardController;
import pppp.group14project.controller.PlayerBoardController;
import pppp.group14project.controller.WallController;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Team13WallIntegrationTest extends ApplicationTest {


    private static GameBoardController gameBoardController;

    private static WallController wallController;

    private static Wall wall;

    private static Board board;

    private static Player player;

    private static PlayerBoardController playerBoardController;


    @Override
    public void start(Stage stage) throws Exception {
        player = new Player("test");

        Game game = Game.getInstance();
        board = new Board(player);
        board.useTeam13Wall();

        game.addBoard(board);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();

        gameBoardController = gameBoard.getController();
        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);
        wallController = playerBoardController.getWallController();
        wall = wallController.getWall();
        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }

    @Test
    void testInitialization() {
        assertNotNull(gameBoardController);
        assertNotNull(wallController);
        assertNotNull(wall);
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
        } catch (FullException | WrongTileException e) {
            fail("Should not throw FullException or WrongTileException");
        }
    }

    @Test
    void wallAddTiles() {
        try {
            wallController.addTileToWall(Tile.RED, 2);
        } catch (FullException | WrongTileException e) {
            fail("Should not throw FullException or WrongTileException");
        }
        assertEquals(1, wall.getTilesInWall().size());
        //No two tiles in the same row
        try {
            wallController.addTileToWall(Tile.RED, 2);
            fail("Expect FullException and WrongTileException");
        } catch (FullException | WrongTileException ignored) {}

        assertEquals(1, wall.getTilesInWall().size());

        try {
            wallController.addTileToWall(Tile.BLUE, 2);
        } catch (FullException | WrongTileException e) {
            fail("Should not throw FullException or WrongTileException");
        }
        assertEquals(2, wall.getTilesInWall().size());
        assertEquals(0, wall.countTilesInRow(0));
        assertEquals(2, wall.countTilesInRow(2));
        wallController.resetWallView();
    }

    @Test
    void wallAddTilesInCorrectSpot() {
        try {
            wallController.addTileToWall(Tile.BLACK, 3);
        } catch (FullException | WrongTileException e) {
            fail("Should not throw FullException or WrongTileException");
        }
        assertEquals(Tile.BLACK, wall.getRow(3).get(1));
        wallController.resetWallView();
    }

}
