package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxToolkit;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.testfx.framework.junit5.ApplicationTest;

public class StartScreenViewTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/start-screen-view.fxml"));
        stage.setScene(new Scene(root,320, 240));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUp () throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testWelcomeText () {
        clickOn("#usernameTextField");
        write("Bob");
        clickOn("#addPlayerButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#welcomeText", hasText("Welcome [Bob]!"));
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#playerName", NodeQueryUtils.hasText("Bob"));

    }
}