package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class PlayerFloorViewTest extends ApplicationTest {
  @BeforeAll
  public static void headless() {
    if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
      GitlabCISupport.headless();
    }
  }


  @Override
  public void start (Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/player-floor-view.fxml"));
    stage.setScene(new Scene(root,320, 240));
    stage.show();
    stage.toFront();
  }

  @BeforeEach
  public void setUp () throws Exception {

  }

  @AfterEach
  public void tearDown () throws Exception {
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }

  @Test
  public void floorTileGridIsDisplayed () {
    verifyThat("#floorGridPane", isVisible());
  }

  @Test
  public void correctNumberOfFloorTileLabelsAreDisplayed () {

    int displayedLabels = 0;
    int expectedLabels = 7;

    GridPane floorGridPane = lookup("#floorGridPane").query();

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Label) {
        displayedLabels++;
      }
    }

    assertEquals(expectedLabels, displayedLabels);
  }

  @Test
  public void correctNumberOfFloorTileBoxesAreDisplayed () {

    int displayedBoxes = 0;
    int expectedBoxes = 7;

    GridPane floorGridPane = lookup("#floorGridPane").query();

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Rectangle) {
        displayedBoxes++;
      }
    }

    assertEquals(displayedBoxes, expectedBoxes);
  }

  @Test
  public void floorTileLabelsDisplayCorrectNegativePoints () {

    GridPane floorGridPane = lookup("#floorGridPane").query();

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Label) {
        String textPoints = ((Label) node).getText();
        assertTrue(textPoints.equals("-1") || textPoints.equals("-2") || textPoints.equals("-3"));
      }
    }
  }

}