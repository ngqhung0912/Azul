package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.TableController;
import pppp.group14project.controller.WallController;
import pppp.group14project.model.Table;
import pppp.group14project.model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.junit.jupiter.api.Assertions.*;


class GameTableViewTest extends ApplicationTest {

    private TableController tableController;

    private GridPane tableGridPane;

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/game-table-view.fxml"));
        FXMLLoader tableLoader  = new FXMLLoader(getClass().getResource("/game-table-view.fxml"));
        tableGridPane  = tableLoader.load();
        tableController = tableLoader.getController();
        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }



    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void tableTileGridIsDisplayed() {
        verifyThat("#tableGridPane", isVisible());
    }

    @Test
    void countAddedTiles() throws Exception {
        Table table = new Table();
        tableGridPane.getStylesheets().add("game-table-view-styles.css");
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tileList.add(Tile.WHITE);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);

        tableController.addTilesToTable(table, tileList);
        int expectedCount = tileList.size() + 1;
        int actualCount = 0;
        for (Node node : tableGridPane.getChildren()){
            if(node.getOpacity() == 1){
                actualCount++;
            }
        }
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void removeAllAddedTiles() throws Exception {
        Table table = new Table();
        tableGridPane.getStylesheets().add("game-table-view-styles.css");
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);

        tableController.addTilesToTable(table, tileList);
        int expectedCount = tileList.size()+1;
        int actualCount = 0;
        int emptyCount = 0;
        for (Node node : tableGridPane.getChildren()){
            if(node.getOpacity() == 1){
                actualCount++;
            }
        }
        assertEquals(expectedCount, actualCount);

        tableController.grabTilesFromTable(table, Tile.BLUE);

        for (Node node : tableGridPane.getChildren()){
            if(node.getOpacity() == 1){
                emptyCount++;
            }
        }
        assertEquals(0, emptyCount);
    }

    // add a test for grabbing tiles
    @Test
    void firstGrabTilesFromTable() throws Exception {
        Table table = new Table();
        tableGridPane.getStylesheets().add("game-table-view-styles.css");
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLUE);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLACK);
        tableController.addTilesToTable(table, tileList);
        assertEquals(5, table.size());
        tableController.grabTilesFromTable(table, Tile.BLUE);
        assertEquals(2, table.size());

    }

}
