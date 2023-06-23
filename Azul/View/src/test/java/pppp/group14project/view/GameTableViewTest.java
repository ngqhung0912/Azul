package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.PlayerBoardController;
import pppp.group14project.controller.TableController;
import pppp.group14project.model.Table;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;


class GameTableViewTest extends ApplicationTest {


    private GridPane tableGridPane;

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/game-table-view.fxml"));
        Parent root = FXMLLoader.load((getClass().getResource("/game-table-view.fxml")));
        tableGridPane = tableLoader.load();
        stage.setScene(new Scene(root, 180, 180));
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


}
