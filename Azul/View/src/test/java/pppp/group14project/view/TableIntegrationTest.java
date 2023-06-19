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
import pppp.group14project.model.exceptions.EmptyException;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableIntegrationTest extends ApplicationTest {
    private GameBoardController gameBoardController;

    private static TableController tableController;

    private static Table table;

    private static Board board;

    private static Player player;



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



        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }

    @Test
    void testInitialization() {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(table);

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
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
        assertEquals(tileList.size() + 1, table.size());
        tableController.selectTilesToGrabFromTable(Tile.BLUE);
        tableController.removeSelectedTilesFromTable();
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
        } catch (FullException e) {
            fail("Should not throw FullException");
        }
        assertEquals(tileList.size() + 1, table.size());
    }
}
