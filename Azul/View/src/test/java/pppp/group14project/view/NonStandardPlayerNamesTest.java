package pppp.group14project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.GameBoardController;
import pppp.group14project.model.*;


import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class NonStandardPlayerNamesTest extends ApplicationTest {

    private static GameBoardController gameBoardController;

    private static Board board1;
    private static Board board2;


    private static Player player1;
    private static Player player2;


    private static Game game;

    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        player1 = new Player("");
        player2 = new Player("thisisverylongplayernameitisactuallyverylonganditgetsevenlongerhowlongcanitactualybe");

        game = Game.getInstance();
        game.getBoardList().clear();
        game.getTilecontainer().reset();
        game.getFactoryList().clear();
        board1 = new Board(player1);
        game.addBoard(board1);

        board2 = new Board(player2);
        game.addBoard(board2);

        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
        gameBoard.load();
        gameBoardController = gameBoard.getController();

        stage.setScene(new Scene(root, 1250, 700));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        game.getFactoryList().clear();
        game.getBoardList().clear();
        game.getTilecontainer().reset();
    }

    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
    }

    @Test
    void unusualPlayerNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.add("");
        nameList.add("thisisverylongplayernameitisactuallyverylonganditgetsevenlongerhowlongcanitactualybe");
        assertEquals(nameList, game.getPlayerNameList());
    }


}
