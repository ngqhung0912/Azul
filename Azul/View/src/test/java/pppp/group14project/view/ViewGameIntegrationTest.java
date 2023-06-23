package pppp.group14project.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import pppp.group14project.controller.*;
import pppp.group14project.model.*;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Wall;
import pppp.group14project.model.exceptions.FullException;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewGameIntegrationTest extends ApplicationTest {

    private GameBoardController gameBoardController;

    private static TableController tableController;

    private static Table table;

    private static WallController wallController;

    private static Wall wall;

    private static FactoryController factoryController;

    private static Factory factory;

    private static FloorController floorController;

    private static Floor floor;

    private static PatternController patternController;

    private static Pattern pattern;

    private static Board board;

    private static Player player;

    private static PlayerBoardController playerBoardController;

    private static Game game;

    private GridPane gameBoardPane;

    private static Parent root;


    @BeforeAll
    public static void headless() {
        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
            GitlabCISupport.headless();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        player = new Player("test");

        game = Game.getInstance();
        board = new Board(player);
        game.addBoard(board);

        List<Factory> factories = game.getFactoryList();

        root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));


        //get game instances more factories
        factories.clear();
        factories.add(new Factory());

        gameBoardPane = gameBoard.load();

        gameBoardController = gameBoard.getController();

        tableController = gameBoardController.getTableController();

        table = tableController.getTable();

        factoryController = gameBoardController.getFactoryControllers().get(0);

        factory = factoryController.getFactory();

        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);

        wallController = playerBoardController.getWallController();
        wall = wallController.getWall();

        patternController = playerBoardController.getPatternController();

        floorController = playerBoardController.getFloorController();

        floor = floorController.getFloor();

        pattern = patternController.getPattern();

        stage.setScene(new Scene(root, 1250, 700));
        stage.show();
        stage.toFront();
    }


    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        game.getFactoryList().clear();
        game.getBoardList().clear();
        game.getTilecontainer().reset();

    }


    @Test
    void testInitialization() throws Exception {
        assertNotNull(gameBoardController);
        assertNotNull(tableController);
        assertNotNull(factoryController);
        assertNotNull(factory);
        assertNotNull(wallController);
        assertNotNull(patternController);
        assertNotNull(floorController);

    }


    @Test
    void testMoveTilesToTable() throws FullException {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLACK);
        tileList.add(Tile.BLUE);
        tableController.addTilesToTable(tileList);
        assertEquals(tileList.size() + 1, table.size());
        List<Tile> tableTile = new ArrayList<>();
        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
        for (Node node : tableGrid.getChildren()) {
            if (node instanceof ClickableTile) {
                Tile color = ((ClickableTile) node).getColour();
                if (color != null) {
                    tableTile.add(color);
                }
            }
        }
        assertEquals(table.getAllCurrentTiles(), tableTile);

    }

    @Test
    void testMoveTilesFromTable() throws FullException {

        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLACK);
        tileList.add(Tile.BLUE);
        tableController.addTilesToTable(tileList);
        assertEquals(tileList.size() + 1, table.size());
        List<Tile> tableTile = new ArrayList<>();
        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
        assertTrue(tableGrid.isVisible());
        assertNotEquals(null, tableGrid);

        mouseClickHandling(tableGrid.getChildren().get(1));

        for (Node node : tableGrid.getChildren()) {
            if (node instanceof ClickableTile) {
                Tile color = ((ClickableTile) node).getColour();
                if (color != null && node.getStyleClass().contains("selected") && node.getOpacity() == 1) {
                    tableTile.add(color);
                }
            }
        }
        List<Tile> expected = new ArrayList<>();
        expected.add(Tile.STARTING);
        expected.add(Tile.BLUE);
        expected.add(Tile.BLUE);
        assertEquals(expected, tableTile);

    }

    @Test
    void selectAndUnselectTilesTable() throws FullException {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.BLUE);
        tileList.add(Tile.BLACK);
        tileList.add(Tile.BLUE);
        tableController.addTilesToTable(tileList);

        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");

        mouseEnterHandling(tableGrid.getChildren().get(1));

        List<Tile> tableTileSelected = new ArrayList<>();
        List<Tile> tableTileUnselected = new ArrayList<>();

        List<Tile> expectedTableTileSelected = new ArrayList<>();
        List<Tile> expectedTableTileUnselected = new ArrayList<>();
        expectedTableTileSelected.add(Tile.STARTING);
        expectedTableTileSelected.add(Tile.BLUE);
        expectedTableTileSelected.add(Tile.BLUE);
        expectedTableTileUnselected.add(Tile.BLACK);

        for (Node node : tableGrid.getChildren()) {
            if (node instanceof ClickableTile && node.getOpacity() == 1) {
                Tile color = ((ClickableTile) node).getColour();
                if (node.getStyleClass().contains("selected")) {
                    tableTileSelected.add(color);
                } else {
                    tableTileUnselected.add(color);
                }
            }
        }

        assertEquals(expectedTableTileSelected, tableTileSelected);
        assertEquals(expectedTableTileUnselected, tableTileUnselected);

        mouseExitedHandling(tableGrid.getChildren().get(1));
        expectedTableTileSelected.clear();
        expectedTableTileUnselected.clear();
        tableTileSelected.clear();
        tableTileUnselected.clear();

        expectedTableTileUnselected.add(Tile.STARTING);
        expectedTableTileUnselected.addAll(tileList);

        for (Node node : tableGrid.getChildren()) {
            if (node instanceof ClickableTile && node.getOpacity() == 1) {
                Tile color = ((ClickableTile) node).getColour();
                if (node.getStyleClass().contains("selected")) {
                    tableTileSelected.add(color);
                } else {
                    tableTileUnselected.add(color);
                }
            }
        }
        assertEquals(expectedTableTileSelected, tableTileSelected);
        assertEquals(expectedTableTileUnselected, tableTileUnselected);

        mouseClickHandling(tableGrid.getChildren().get(2));

        expectedTableTileSelected.clear();
        expectedTableTileUnselected.clear();
        tableTileSelected.clear();
        tableTileUnselected.clear();

        expectedTableTileSelected.add(Tile.STARTING);
        expectedTableTileSelected.add(Tile.BLACK);
        expectedTableTileUnselected.add(Tile.BLUE);
        expectedTableTileUnselected.add(Tile.BLUE);

        for (Node node : tableGrid.getChildren()) {
            if (node instanceof ClickableTile && node.getOpacity() == 1) {
                Tile color = ((ClickableTile) node).getColour();
                if (node.getStyleClass().contains("selected")) {
                    tableTileSelected.add(color);
                } else {
                    tableTileUnselected.add(color);
                }
            }
        }

        assertEquals(expectedTableTileSelected, tableTileSelected);
        assertEquals(expectedTableTileUnselected, tableTileUnselected);

    }

    @Test
    void moveTilesFromFactory() {
        factory.empty();
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);
        tileList.add(Tile.RED);
        game.fillFactories(tileList);
        assertEquals(tileList.size(), factory.size());
        System.out.println(table.getTiles());
        GridPane factoriesGrid = (GridPane) gameBoardPane.lookup("#factoriesGrid");
        GridPane tileGrid = (GridPane) factoriesGrid.lookup("#tileGrid");
        mouseClickHandling(tileGrid.getChildren().get(0));

        List<Tile> expected = new ArrayList<>();
        List<Tile> actual = new ArrayList<>();
        expected.add(Tile.RED);
        expected.add(Tile.RED);

        for (Node node : tileGrid.getChildren()) {
            if (node instanceof ClickableTile && node.getStyleClass().contains("selected")) {
                actual.add(((ClickableTile) node).getColour());
            }
        }
        assertEquals(expected, actual);
        factory.empty();
    }

    @Test
    void moveTilesFromTableToPattern() throws FullException {
        factory.empty();

        GridPane tableGridP = (GridPane) gameBoardPane.lookup("#tableGridPane");
        StackPane patternPaneP = (StackPane) gameBoardPane.lookup("#patternPane");
        GridPane floorGridP = (GridPane) gameBoardPane.lookup("#floorGridPane");

        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.ORANGE);
        tileList.add(Tile.BLACK);
        tileList.add(Tile.RED);

        tableController.addTilesToTable(tileList);

        assertEquals(tileList.size() + 1, table.size());

        ClickableTile clickedTile = (ClickableTile) tableGridP.getChildren().get(1);
        mouseClickHandling(clickedTile);

        VBox row = (VBox) patternPaneP.getChildren().get(0);
        HBox col = (HBox) row.getChildren().get(0);
        Node space = col.getChildren().get(0);

        Node node = floorGridP.getChildren().get(7);

        assertTrue(space.getStyleClass().contains("tile-option"));
        assertTrue(node.getStyleClass().contains("tile-option"));

    }

    @Test
    void moveTilesFromFactoryToPatternFloorTableWall() throws WrongTileException {
        factory.empty();
        List<Tile> tileList = new ArrayList<>();
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.RED);
        tileList.add(Tile.BLUE);
        game.fillFactories(tileList);

        assertEquals(tileList, factory.getTiles());

        patternController.grabTilesWhenPatternHaveSpaces(factory, Tile.RED, 1);

        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
        StackPane patternPane = (StackPane) gameBoardPane.lookup("#patternPane");
        GridPane floorGrid = (GridPane) gameBoardPane.lookup("#floorGridPane");
        GridPane wallGrid = (GridPane) gameBoardPane.lookup("#wallGridPane");

        VBox row = (VBox) patternPane.getChildren().get(0);
        HBox col = (HBox) row.getChildren().get(1);
        Node space = col.getChildren().get(0);

        assertTrue(space.getStyleClass().contains("RED"));
        assertTrue(patternController.getPattern().getPatternLines().get(1).isFull());

        Node node = floorGrid.getChildren().get(7);

        System.out.println(floor.getTiles());

        assertTrue(node.getStyleClass().contains("RED"));
        assertEquals(Tile.RED, floorController.getFloor().getTiles().get(0));

        ClickableTile tableTile = (ClickableTile) tableGrid.getChildren().get(1);

        List<Tile> tableList = new ArrayList<>();
        tableList.add(Tile.STARTING);
        tableList.add(Tile.BLUE);

        assertTrue(tableTile.getStyleClass().contains("BLUE"));
        assertEquals(tableList, table.getAllCurrentTiles());

        playerBoardController.moveTileFromPatternToWall();

        Node wallTile = null;

        for (Node tile : wallGrid.getChildren()) {
            if (wallGrid.getRowIndex(tile) == 1 && wallGrid.getColumnIndex(tile) == 3) {
                wallTile = tile;
                break;
            }
        }

        assertEquals(1, wallTile.getOpacity());
        assertEquals(Tile.RED, wall.getTilesInWall().get(0));
    }


    @Test
    public void endGame() throws WrongTileException, FullException {
        wall.addTile(Tile.WHITE, 0);
        wall.addTile(Tile.BLUE, 0);
        wall.addTile(Tile.RED, 0);
        wall.addTile(Tile.BLACK, 0);
        wall.addTile(Tile.ORANGE, 0);

        factory.getTiles().clear();
        table.getTiles().clear();

        gameBoardController.finishPlayerTurn();

        assertTrue(playerBoardController.playerName.getStyleClass().contains("winnerText"));
        assertTrue(playerBoardController.boardBackground.getStyleClass().contains("winnerBoard"));
        assertTrue(playerBoardController.getScoreController().scoreText.getStyleClass().contains("winnerText"));
    }

    void mouseClickHandling(Node node) {
        Event.fireEvent(node, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));

    }

    void mouseEnterHandling(Node node) {
        Event.fireEvent(node, new MouseEvent(MouseEvent.MOUSE_ENTERED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }

    void mouseExitedHandling(Node node) {
        Event.fireEvent(node, new MouseEvent(MouseEvent.MOUSE_EXITED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }


}



