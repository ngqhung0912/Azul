package pppp.group14project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  private final Game game = Game.getInstance();

  @Test
  void gameCanMaximallyHaveFourBoards() {
    assertTrue(game.addBoard(new Board(new Player("Player1"))));
    assertTrue(game.addBoard(new Board(new Player("Player2"))));
    assertTrue(game.addBoard(new Board(new Player("Player3"))));
    assertTrue(game.addBoard(new Board(new Player("Player4"))));
    assertFalse(game.addBoard(new Board(new Player("Player5"))));
  }
}