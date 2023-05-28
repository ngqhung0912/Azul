package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.WallController;
import pppp.group14project.model.Tile;
import pppp.group14project.model.Wall;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerWallViewTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/player-wall-view.fxml"));
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUp() throws Exception {

    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void wallTileGridIsDisplayed() {
        verifyThat("#wallGridPane", isVisible());
    }

    @Test
    public void correctNumberOfTilesIsDisplayed() {
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
    public void correctInitialTileOpacity() {
        int expectedTiles = 25;
        int displayedTiles = 0;
        double initialOpacity = 0.3;

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
    public void addTilesToWall() {
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
                if (node.getOpacity() == finalOpacity && ((Rectangle) node).getWidth() == 70 && ((Rectangle) node).getHeight() == 70) {
                    displayedTiles++;
                }
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    public void resetWallView(){
        int expectedTiles = 3;
        int expectedTilesAfterReset = 25;
        int displayedTilesFirst = 0;
        int displayedTilesAfterReset = 0;
        double initialOpacity = 0.3;
        double finalOpacity = 1;
        GridPane wallGridPane = lookup("#wallGridPane").query();
        WallController wallController = new WallController();
        Wall wall = new Wall();
        wallController.addTileToWall(wall, Tile.RED, 2, wallGridPane);
        wallController.addTileToWall(wall, Tile.ORANGE, 3, wallGridPane);
        wallController.addTileToWall(wall, Tile.BLACK, 4, wallGridPane);

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == finalOpacity && ((Rectangle) node).getWidth() == 70 && ((Rectangle) node).getHeight() == 70) {
                    displayedTilesFirst++;
                }
            }
        }
        assertEquals(expectedTiles, displayedTilesFirst);
        wallController.resetWallView(wallGridPane);
        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (node.getOpacity() == initialOpacity && ((Rectangle) node).getWidth() == 50 && ((Rectangle) node).getHeight() == 50) {
                    displayedTilesAfterReset++;
                }
            }
        }
        assertEquals(expectedTilesAfterReset, displayedTilesAfterReset);

    }
}