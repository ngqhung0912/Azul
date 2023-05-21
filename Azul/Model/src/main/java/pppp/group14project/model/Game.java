package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private static Game instance = null;
  private int currentRound;

  @Getter
  private List<Board> boardlist = new ArrayList<Board>();

  private Game() {
    currentRound = 1;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  public Boolean addBoard(Board board) {
    if (boardlist.size() < 4) {
      boardlist.add(board);
      return true;
    }
    return false;
  }
}
