package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class GameBoardViewTest extends ApplicationTest {
  @Override
  public void start (Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/start-screen-view.fxml"));
    stage.setScene(new Scene(root,320, 240));
    stage.show();
    stage.toFront();
  }

  @Before
  public void setUp () throws Exception {
  }

  @After
  public void tearDown () throws Exception {
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }

  @Test
  public void showPlayerNameOnGameBoardWhenGameIsStarted () {
    String playerName = "Player";

    clickOn("#usernameTextField");
    write(playerName);
    clickOn("#addPlayerButton");
    clickOn("#startButton");

    verifyThat("#playerName", hasText(playerName));
  }

}