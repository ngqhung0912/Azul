package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import pppp.group14project.model.exceptions.WrongTileException;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class ScoreIntegrationTest extends ApplicationTest {

    private static Board board;

    private static Player player;

    private static PlayerBoardController playerBoardController;

    private static GameBoardController gameBoardController;

    private GridPane gameBoardPane;

    private static ScoreController scoreController;

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
        game.getBoardList().clear();
        game.getTilecontainer().reset();
        game.getFactoryList().clear();
        board = new Board(player);

        game.addBoard(board);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoardPane = gameBoard.load();

        gameBoardController = gameBoard.getController();

        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        scoreController = playerBoardController.getScoreController();

        stage.setScene(new Scene(root, 120, 600));
        stage.show();
        stage.toFront();

    }

    @Test
    void checkScore(){
        board.getScore().setValue(5);
        Text text = (Text) gameBoardPane.lookup("#scoreText");
        String score = text.getText();
        assertEquals("5", score);
    }


}
