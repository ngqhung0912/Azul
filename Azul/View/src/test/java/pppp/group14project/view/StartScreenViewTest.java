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

    @Test
    public void testPlayerName() {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#playerName", NodeQueryUtils.hasText("Bob"));
    }

    @Test
    public void testNoName() {
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#playerName", NodeQueryUtils.hasText(""));
    }

    @Test
    public void correctNumberOfElementsTwoPlayers() {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(1, countObjectInGrid("playerBoardGrid", innerGridLeft));
        assertEquals(1, countObjectInGrid("playerBoardGrid", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(5, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    @Test
    public void correctNumberOfElementsThreePlayers() {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(2, countObjectInGrid("playerBoardGrid", innerGridLeft));
        assertEquals(1, countObjectInGrid("playerBoardGrid", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(7, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    @Test
    public void correctNumberOfElementsFourPlayers() {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        GridPane innerGridLeft = lookup("#innerGridLeft").query();
        GridPane innerGridRight = lookup("#innerGridRight").query();
        assertEquals(2, countObjectInGrid("playerBoardGrid", innerGridLeft));
        assertEquals(2, countObjectInGrid("playerBoardGrid", innerGridRight));
        GridPane factoriesGrid = lookup("#factoriesGrid").query();
        assertEquals(9, countObjectInGrid("factoryGrid", factoriesGrid));
    }

    private int countObjectInGrid(String id, GridPane grid) {
        int count = 0;
        for(Node n: grid.getChildren()) {
            if(n.getId().equals(id)) {
                count++;
            }
        }
        return count;
    }
}