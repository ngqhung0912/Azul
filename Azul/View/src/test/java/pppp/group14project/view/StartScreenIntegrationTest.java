package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;
import pppp.group14project.model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class StartScreenIntegrationTest extends ApplicationTest {
    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/start-screen-view.fxml"));
        stage.setScene(new Scene(root, 1250, 700));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUp() throws Exception {
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
    void testPlayerName() {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#usernameTextField");
        write("Robbert");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#playerName", NodeQueryUtils.hasText("Bob"));
    }

    @Test
    void testNoName() {
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#playerName", NodeQueryUtils.hasText(""));
    }

    @Test
    void correctNumberOfElementsTwoPlayers() {
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(1, countObjectInGrid("boardview", innerGridLeft));
        assertEquals(1, countObjectInGrid("boardview", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(5, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    @Test
    void correctNumberOfElementsThreePlayers() {
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(2, countObjectInGrid("boardview", innerGridLeft));
        assertEquals(1, countObjectInGrid("boardview", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(7, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    @Test
    void correctNumberOfElementsFourPlayers() {
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(2, countObjectInGrid("boardview", innerGridLeft));
        assertEquals(2, countObjectInGrid("boardview", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(9, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    private int countObjectInGrid(String id, GridPane grid) {
        int count = 0;
        for (Node n : grid.getChildren()) {
            if (n.getId().equals(id)) {
                count++;
            }
        }
        return count;
    }
}
