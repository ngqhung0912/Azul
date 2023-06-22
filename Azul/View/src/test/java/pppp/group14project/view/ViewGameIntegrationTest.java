//package pppp.group14project.view;
//
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.MouseButton;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.testfx.api.FxToolkit;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.util.WaitForAsyncUtils;
//import pppp.group14project.controller.*;
//import pppp.group14project.model.*;
//import org.junit.jupiter.api.Test;
//import pppp.group14project.model.Wall;
//import pppp.group14project.model.exceptions.FullException;
//import static org.testfx.api.FxAssert.verifyThat;
//
//import javafx.event.Event;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import pppp.group14project.model.exceptions.WrongTileException;
//
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.testfx.util.NodeQueryUtils.isVisible;
//
//class ViewGameIntegrationTest extends ApplicationTest {
//
//    private GameBoardController gameBoardController;
//
//    private static TableController tableController;
//
//    private static Table table;
//
//    private static WallController wallController;
//
//    private static Wall wall;
//
//    private static FactoryController factoryController;
//
//    private static Factory factory;
//
//    private static FloorController floorController;
//
//    private static Floor floor;
//
//    private static PatternController patternController;
//
//    private static Pattern pattern;
//
//    private static Board board;
//
//    private static Player player;
//
//    private static PlayerBoardController playerBoardController;
//
//    private static Game game;
//
//    private GridPane gameBoardPane;
//
//
//
//    @BeforeAll
//    public static void headless() {
//        if (Boolean.parseBoolean(System.getProperty("gitlab-ci", "false"))) {
//            GitlabCISupport.headless();
//        }
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        player = new Player("test");
//
//        game = Game.getInstance();
//        board = new Board(player);
//        game.addBoard(board);
//
//        List<Factory> factories = game.getFactoryList();
//
//        Parent root = FXMLLoader.load(getClass().getResource("/game-board-view.fxml"));
//        FXMLLoader gameBoard = new FXMLLoader(getClass().getResource("/game-board-view.fxml"));
//
//
//
//        //get game instances more factories
//        factories.clear();
//        factories.add(new Factory());
//
//        gameBoardPane = gameBoard.load();
//
//        gameBoardController = gameBoard.getController();
//
//        tableController = gameBoardController.getTableController();
//        table = tableController.getTable();
//
//        factoryController = gameBoardController.getFactoryControllers().get(0);
//
//        factory = factoryController.getFactory();
//
//        playerBoardController = gameBoardController.getPlayerBoardControllers().get(0);
//
//        wallController = playerBoardController.getWallController();
//
//        patternController = playerBoardController.getPatternController();
//
//        floorController = playerBoardController.getFloorController();
//
//        floor = floorController.getFloor();
//
//        pattern = patternController.getPattern();
//
//
//        stage.setScene(new Scene(root,1250, 700));
//        stage.show();
//        stage.toFront();
//    }
//
//
//    @AfterEach
//    public void tearDown() throws Exception{
//        FxToolkit.hideStage();
//        release(new KeyCode[]{});
//        release(new MouseButton[]{});
//        gameBoardController.endGame();
//    }
//
//
//
//    @Test
//    void testInitialization() throws Exception {
//        assertNotNull(gameBoardController);
//        assertNotNull(tableController);
//        assertNotNull(factoryController);
//        assertNotNull(factory);
//        assertNotNull(wallController);
//        assertNotNull(patternController);
//        assertNotNull(floorController);
//
//    }
//
//
//    @Test
//    void testMoveTilesToTable() throws FullException {
//        List<Tile> tileList = new ArrayList<>();
//        tileList.add(Tile.BLUE);
//        tileList.add(Tile.BLACK);
//        tileList.add(Tile.BLUE);
//        tableController.addTilesToTable(tileList);
//        assertEquals(tileList.size()+1, table.size());
//        List<Tile> tableTile = new ArrayList<>();
//        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
//        for(Node node : tableGrid.getChildren()){
//            if(node instanceof ClickableTile){
//                Tile color = ((ClickableTile) node).getColour();
//                if (color != null){
//                    tableTile.add(color);
//                }
//            }
//        }
//        assertEquals(table.getAllCurrentTiles(), tableTile);
//
//    }
//
//    @Test
//    void testMoveTilesFromTable() throws FullException {
//
//        List<Tile> tileList = new ArrayList<>();
//        tileList.add(Tile.BLUE);
//        tileList.add(Tile.BLACK);
//        tileList.add(Tile.BLUE);
//        tableController.addTilesToTable(tileList);
//        assertEquals(tileList.size()+1, table.size());
//        List<Tile> tableTile = new ArrayList<>();
//        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
//        assertTrue(tableGrid.isVisible());
//        assertNotEquals(null, tableGrid);
//
//        mouseClickHandling(tableGrid.getChildren().get(1));
//
//        for(Node node : tableGrid.getChildren()){
//            if(node instanceof ClickableTile){
//                Tile color = ((ClickableTile) node).getColour();
//                if (color != null && node.getStyleClass().contains("selected") && node.getOpacity() == 1){
//                    tableTile.add(color);
//                }
//            }
//        }
//        List<Tile> expected = new ArrayList<>();
//        expected.add(Tile.STARTING);
//        expected.add(Tile.BLUE);
//        expected.add(Tile.BLUE);
//        assertEquals(expected, tableTile);
//
//    }
//
//    @Test
//    void moveTilesFromFactory(){
//        factory.empty();
//        List<Tile> tileList = new ArrayList<>();
//        tileList.add(Tile.RED);
//        tileList.add(Tile.ORANGE);
//        tileList.add(Tile.BLACK);
//        tileList.add(Tile.RED);
//        game.fillFactories(tileList);
//        assertEquals(tileList.size(), factory.size());
//        System.out.println(table.getTiles());
//        GridPane factoriesGrid = (GridPane) gameBoardPane.lookup("#factoriesGrid");
//        GridPane tileGrid = (GridPane) factoriesGrid.lookup("#tileGrid");
//        mouseClickHandling(tileGrid.getChildren().get(0));
//
//        List<Tile> expected = new ArrayList<>();
//        List<Tile> actual = new ArrayList<>();
//        expected.add(Tile.RED);
//        expected.add(Tile.RED);
//
//        for (Node node: tileGrid.getChildren()){
//            if (node instanceof ClickableTile && node.getStyleClass().contains("selected")){
//                actual.add(((ClickableTile) node).getColour());
//            }
//        }
//        assertEquals(expected, actual);
//        factory.empty();
//    }
//
//    @Test
//    void moveTilesFromTableToPattern() throws FullException, WrongTileException {
//        List<Tile> tileList = new ArrayList<>();
//        tileList.add(Tile.BLUE);
//        tileList.add(Tile.ORANGE);
//        tileList.add(Tile.BLUE);
//        tileList.add(Tile.RED);
//        tableController.addTilesToTable(tileList);
//        floor.emptyFloor();
//        assertEquals(tileList.size()+1, table.size());
//        List<Tile> tableTile = new ArrayList<>();
//        GridPane tableGrid = (GridPane) gameBoardPane.lookup("#tableGridPane");
//        StackPane patternPane = (StackPane) gameBoardPane.lookup("#patternPane");
//        GridPane floorGrid = (GridPane) gameBoardPane.lookup("#floorGridPane");
//        assertTrue(patternPane.isVisible());
//        assertNotEquals(null, patternPane);
//        assertTrue(tableGrid.isVisible());
//        assertNotEquals(null, tableGrid);
//
//        ClickableTile clickedTile= (ClickableTile) tableGrid.getChildren().get(1);
//        mouseClickHandling(clickedTile);
//
//
//
//        VBox row = (VBox) patternPane.lookup("#rows");
//        HBox col = (HBox) row.getChildren().get(0);
//        Space space = (Space) col.getChildren().get(0);
//        //TODO ask how the pattern works
//        mouseClickSpaceHandling(space);
//        System.out.println(space);
//        System.out.println(pattern.getPatternLines().get(0).getSpaces().get(0));
//
////        assertTrue(pattern.getPatternLines().get(0).isFull());
////        assertEquals(floor.getTiles(), Tile.STARTING);
//
//
//    }
//
//    void mouseClickHandling(Node node){
//        Event.fireEvent(node, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
//                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
//                true, true, true, true, true, true, null));
//
//    }
//
//    void mouseClickSpaceHandling(Space space){
//        Event.fireEvent(space, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
//                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
//                true, true, true, true, true, true, null));
//    }
//
//
//}
//
//
//
