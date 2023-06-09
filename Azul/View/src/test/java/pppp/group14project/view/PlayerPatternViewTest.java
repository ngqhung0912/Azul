package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPatternViewTest extends ApplicationTest {

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/player-pattern-view.fxml"));
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void wallTileGridIsDisplayed() {
        verifyThat("#patternPane", isVisible());
    }


    @Test
    void correctNumberOfTilesIsDisplayed() {

        StackPane pattern = lookup("#patternPane").query();

        VBox vbox = ((VBox) pattern.getChildren().get(0));
        int numberOfRows = vbox.getChildren().size();
        assertEquals(numberOfRows, 5);

        for (int i = 0; i < numberOfRows; i++) {
            HBox h = (HBox) vbox.getChildren().get(i);
            int numberOfSpaces = h.getChildren().size();
            assertEquals(numberOfSpaces, i + 1);
        }

    }

    @Test
    void noTilesColored() {

        StackPane pattern = lookup("#patternPane").query();

        VBox vbox = ((VBox) pattern.getChildren().get(0));
        int numberOfRows = vbox.getChildren().size();

        for (Node r: vbox.getChildren()) {
            HBox hbox = (HBox) r;
            for(Node s: hbox.getChildren()) {
                assertFalse(s.getStyleClass().contains("is-colored"));
            }
        }

    }

}
