package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatternIntegrationTest extends ApplicationTest {
    private static GameBoardController gameBoardController;

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

        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        patternController = playerBoardController.getPatternController();
        pattern = patternController.getPattern();



        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }

    @Test
    void testInitialization() {
        assertNotNull(gameBoardController);
//        assertNotNull(factoryController);
        //TODO fix the way factories are passed
//        assertNotNull(factory);
        assertNotNull(patternController);
        assertNotNull(pattern);

    }


    @Test
    void testAddAndRemoveCorrectTilesToPattern() {
        List<Tile> tileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
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
    void testAddDifferentColorToSamePattern() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        try {
            pattern.addTiles(1, tileList);
            fail("Expect WrongTileException");
        } catch (WrongTileException ignored) {}
    }

}
