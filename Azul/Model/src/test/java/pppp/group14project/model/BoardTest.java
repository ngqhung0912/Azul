package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import static org.junit.jupiter.api.Assertions.fail;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        Player tester = new Player("Tester");
        board = new Board(tester);
    }


    @Test
    void testUseTeam13WallAndUpdateScore() {
        board.useTeam13Wall();
        assert(board.getWall() instanceof WallAdapter);

        assertBoardScore(0);

        try {
            board.getWall().addTile(Tile.BLUE, 0);
            board.getFloor().addTile(Tile.BLUE);
            board.getFloor().addTile(Tile.BLUE);
            board.getWall().addTile(Tile.BLUE, 1);
            // Turn ends, update score
            assertBoardScore(0);
            board.getFloor().emptyFloor();
            // New turn
            board.getWall().addTile(Tile.RED, 0);
            board.getWall().addTile(Tile.ORANGE, 1);
            board.getWall().addTile(Tile.BLUE, 2);
            // score now: 7

            board.getFloor().addTile(Tile.STARTING);
            board.getFloor().addTile(Tile.BLUE);
            // Turn ends, update score
            assertBoardScore(6);
            board.getFloor().emptyFloor();
        } catch (FullException | WrongTileException e) {
            fail("Should not throw");;
        }



    }

    /**
     * Test the updateScore method.
     * Expected behavior: Before the round ends, the negative score count. However, aft
     */
    @Test
    void testUpdateScore()  {

        assertBoardScore(0);

        try {
            board.getWall().addTile(Tile.BLUE, 0);
            board.getFloor().addTile(Tile.BLUE);
            board.getFloor().addTile(Tile.BLUE);
            board.getWall().addTile(Tile.BLUE, 1);
            // Turn ends, update score
            assertBoardScore(0);
            board.getFloor().emptyFloor();
            // New turn
            board.getWall().addTile(Tile.RED, 0);
            board.getWall().addTile(Tile.ORANGE, 1);
            board.getWall().addTile(Tile.BLUE, 2);
            // score now: 7

            board.getFloor().addTile(Tile.STARTING);
            board.getFloor().addTile(Tile.BLUE);
            // Turn ends, update score
            assertBoardScore(6);
            board.getFloor().emptyFloor();
        } catch (FullException | WrongTileException e) {
            fail("Should not throw");;
        }

    }


    void assertBoardScore(int score) {
        board.updateScore();
        assert(board.getScore().intValue() == score);
    }





}
