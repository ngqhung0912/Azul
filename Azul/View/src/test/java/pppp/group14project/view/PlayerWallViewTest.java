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
        stage.setScene(new Scene(root, 320, 240));
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
            if (node instanceof Rectangle){
                displayedTiles++;
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

    @Test
    public void correctInitialTileOpacity() {
        int expectedTiles = 25;
        int displayedTiles = 0;
        double initialOpacity = 0.5;

        GridPane wallGridPane = lookup("#wallGridPane").query();

        for (Node node : wallGridPane.getChildren()) {
            if (node instanceof Rectangle){
                if (node.getOpacity() == initialOpacity ){
                    displayedTiles++;
                }
            }
        }
        assertEquals(displayedTiles, expectedTiles);
    }

}