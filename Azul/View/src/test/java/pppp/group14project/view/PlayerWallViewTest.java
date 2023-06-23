package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.WallController;
import pppp.group14project.model.Wall;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWallViewTest extends ApplicationTest {

    private WallController wallController;

    private GridPane wallGridPane;

    private Wall wall;

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader wallLoader = new FXMLLoader(getClass().getResource("/player-wall-view.fxml"));
        Parent root = FXMLLoader.load((getClass().getResource("/player-wall-view.fxml")));
        wallGridPane = wallLoader.load();
        wallController = wallLoader.getController();
        wall = new Wall();
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

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Button) {
                displayedTiles++;
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    void correctInitialTileOpacity() {


        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Button) {
                assertFalse(node.getStyleClass().contains("is-colored"));
            }
        }

    }


}