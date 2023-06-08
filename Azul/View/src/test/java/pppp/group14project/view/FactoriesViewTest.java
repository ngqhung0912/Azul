package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import pppp.group14project.controller.ClickableTile;
import pppp.group14project.controller.FactoryController;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;


public class FactoriesViewTest extends ApplicationTest {
    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/factory-view.fxml"));
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void tilesAreClickable() {
        GridPane tileGrid = lookup("#tileGrid").query();
        int expected = 4;
        int actual = 0;
        for(Node n: tileGrid.getChildren()) {
            if(n instanceof ClickableTile) {
                actual++;
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    public void tilesLookClickable() {
        GridPane tileGrid = lookup("#tileGrid").query();
        for(Node n : tileGrid.getChildren()) {
            ClickableTile tile = (ClickableTile) n;
            Paint bordercolour = tile.getBorder().getStrokes().get(0).getLeftStroke();
            Paint backgroundcolor = tile.getBackground().getFills().get(0).getFill();
            assertNotEquals(bordercolour, backgroundcolor);
            moveTo(tile);
            WaitForAsyncUtils.waitForFxEvents();
            backgroundcolor = tile.getBackground().getFills().get(0).getFill();
            assertEquals(bordercolour, backgroundcolor);
        }

    }

}
