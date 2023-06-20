package pppp.group14project.view;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.model.Game;
import pppp.group14project.model.TileContainer;

public class StartScreenViewTest extends ApplicationTest {
    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/start-screen-view.fxml"));
        stage.setScene(new Scene(root,1250, 700));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUp () throws Exception {
        Game game = Game.getInstance();
        game.getBoardList().clear();
        game.getFactoryList().clear();
        game.getTilecontainer().reset();
        game.getPlayerNameList().clear();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        Game game = Game.getInstance();
        game.getBoardList().clear();
        game.getFactoryList().clear();
        game.getTilecontainer().reset();
        game.getPlayerNameList().clear();
    }

    @Test
    public void testWelcomeText () {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#welcomeText", hasText("Welcome [Bob]!"));
    }
}