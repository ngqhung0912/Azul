package pppp.group14project.controller;

import static org.mockito.Mockito.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest extends ApplicationTest {

    @InjectMocks
    private GameBoardController gameBoardController;

    private static TableController tableController;

    private static Table table;


    @BeforeAll
    public void start() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
//        GameBoardController gameBoardController = loader.getController();
        GameBoardController gameBoardController = new GameBoardController();
        URL url = new URL("http://example.com/");
        ResourceBundle rb = null;
        gameBoardController.initialize(url, rb);   // Perhaps really dumb approach

//        tableController = gameBoardController.getTableController();
        table = tableController.getTable();
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/Azul/View/src/main/resources/game-board-view.fxml"));
//        FXMLLoader gameBoard  = new FXMLLoader(getClass().getResource("/Azul/View/src/main/resources/game-board-view.fxml"));
//        gameBoardController = gameBoard.getController();
//        tableController = gameBoardController.getTableController();
//        table = tableController.getTable();
//        stage.setScene(new Scene(root, 120, 600));
//        stage.show();
//        stage.toFront();
//    }


    @Test
    void tableRemoveAllAddedTiles() throws Exception {
        assertNotNull(tableController);
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
