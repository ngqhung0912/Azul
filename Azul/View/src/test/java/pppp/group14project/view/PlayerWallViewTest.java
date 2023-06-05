package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.WallController;
import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWallViewTest extends ApplicationTest {

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/player-wall-view.fxml"));
        stage.setScene(new Scene(root, 180, 180));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void wallTileGridIsDisplayed() {
        verifyThat("#wallGridPane", isVisible());
    }

    @Test
    void correctNumberOfTilesIsDisplayed() {
        int expectedTiles = 25;
        int displayedTiles = 0;

        GridPane wallGridPane = lookup("#wallGridPane").query();

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                displayedTiles++;
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    void correctInitialTileOpacity() {
        int expectedTiles = 25;
        int displayedTiles = 0;
        double initialOpacity = 0.5;

        GridPane wallGridPane = lookup("#wallGridPane").query();

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == initialOpacity) {
                    displayedTiles++;
                }
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    void addTilesToWall() {
        int expectedTiles = 1;
        int displayedTiles = 0;
        double finalOpacity = 1;
        GridPane wallGridPane = lookup("#wallGridPane").query();
        WallController wallController = new WallController();
        Wall wall = new Wall();
        int row = 2;
        wallController.addTileToWall(wall, Tile.RED, row, wallGridPane);
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == finalOpacity && ((Rectangle) node).getStrokeWidth() == 3) {
                    displayedTiles++;
                }
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    void tilesAddedInCorrectSpot() {
        GridPane wallGridPane = lookup("#wallGridPane").query();
        WallController wallController = new WallController();
        Wall wall = new Wall();
        wallController.addTileToWall(wall, Tile.ORANGE, 2, wallGridPane);
        int row = 0;
        int col = 0;
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == 1 && ((Rectangle) node).getStrokeWidth() == 3) {
                    row = GridPane.getRowIndex(node);
                    col = GridPane.getColumnIndex(node);
                }
            }
        }
        assertEquals(2, row);
        //in row 2 the orange tile is on the 3rd column (starting from 0)
        assertEquals(3, col);


    }

    @Test
    void resetWallView() {
        int expectedTiles = 3;
        int expectedTilesAfterReset = 25;
        int displayedTilesFirst = 0;
        int displayedTilesAfterReset = 0;
        double initialOpacity = 0.5;
        double finalOpacity = 1;
        GridPane wallGridPane = lookup("#wallGridPane").query();
        WallController wallController = new WallController();
        Wall wall = new Wall();
        wallController.addTileToWall(wall, Tile.RED, 2, wallGridPane);
        wallController.addTileToWall(wall, Tile.ORANGE, 3, wallGridPane);
        wallController.addTileToWall(wall, Tile.BLACK, 4, wallGridPane);

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == finalOpacity && ((Rectangle) node).getStrokeWidth() == 3) {
                    displayedTilesFirst++;
                }
            }
        }
        assertEquals(expectedTiles, displayedTilesFirst);
        wallController.resetWallView(wallGridPane);
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == initialOpacity && ((Rectangle) node).getStrokeWidth() == 1) {
                    displayedTilesAfterReset++;
                }
            }
        }
        assertEquals(expectedTilesAfterReset, displayedTilesAfterReset);
    }
}