package pppp.group14project.view;

import static org.mockito.Mockito.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pppp.group14project.controller.GameBoardController;
import pppp.group14project.controller.PlayerBoardController;
import pppp.group14project.controller.TableController;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
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

    private static List<PlayerBoardController> playerBoardControllers;


    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard  = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();

        gameBoardController = gameBoard.getController();

        tableController = gameBoardController.getTableController();
        table = tableController.getTable();

        playerBoardControllers = gameBoardController.getPlayerBoardControllers();





        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(table);
        assertNotNull(playerBoardControllers);
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
}

