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
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class PlayerFloorViewTest extends ApplicationTest {
  @Override
  public void start (Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/player-floor-view.fxml"));
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

    Assert.assertEquals(expectedLabels, displayedLabels);
  }

  @Test
  public void correctNumberOfFloorTileBoxesAreDisplayed () {

    int displayedBoxes = 0;
    int expectedBoxes = 7;

    GridPane floorGridPane = lookup("#floorGridPane").query();

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Button) {
        displayedBoxes++;
      }
    }

    Assert.assertEquals(displayedBoxes, expectedBoxes);
  }

  @Test
  public void floorTileLabelsDisplayCorrectNegativePoints () {

    GridPane floorGridPane = lookup("#floorGridPane").query();

    for (Node node : floorGridPane.getChildren()) {
      if (node instanceof Label) {
        String textPoints = ((Label) node).getText();
        Assert.assertTrue(textPoints.equals("-1") || textPoints.equals("-2") || textPoints.equals("-3"));
      }
    }
  }

}