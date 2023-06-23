package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;


import static org.junit.jupiter.api.Assertions.*;

public class PatternIntegrationTest extends ApplicationTest {
    private static GameBoardController gameBoardController;

    private static PatternController patternController;

    private static Pattern pattern;

    private static Board board;

    private static Player player;

    private static PlayerBoardController playerBoardController;

    private GridPane gameBoardPane;


    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        player = new Player("test");

        Game game = Game.getInstance();
        board = new Board(player);

        game.addBoard(board);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoardPane = gameBoard.load();

        gameBoardController = gameBoard.getController();

        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        patternController = playerBoardController.getPatternController();
        pattern = patternController.getPattern();


        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();
    }

    @Test
    void testInitialization() {
        assertNotNull(gameBoardController);
        assertNotNull(patternController);
        assertNotNull(pattern);

    }

    @Test
    void testSpaceLocation() {
        StackPane patternPane = (StackPane) gameBoardPane.lookup("#patternPane");
        VBox rows = (VBox) patternPane.lookup("#rows");
        HBox cols = (HBox) rows.getChildren().get(0);
        Space space = (Space) cols.getChildren().get(0);

        assertEquals(0, space.getRow());
        assertEquals(0, space.getIndex());

    }

    @Test
    void testSpacePlacement() {
        Space space = new Space();
        space.setIndex(2);
        space.setRow(3);
        assertEquals(2, space.getIndex());
        assertEquals(3, space.getRow());
    }

    @Test
    void testSpaceIntegerProperty() {
        Space space = new Space();
        space.rowProperty().set(5);
        space.indexProperty().set(1);
        assertEquals(1, space.getIndex());
        assertEquals(5, space.getRow());
    }

}
