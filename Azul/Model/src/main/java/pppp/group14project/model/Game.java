package pppp.group14project.model;

import lombok.Getter;

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
  
  //TODO (REFACTOR): This method should be throwing an exception if you want to notify that no more board can be added since its full.
  public Boolean addBoard(Board board) {
    if (boardlist.size() < 4) {
      boardlist.add(board);
      return true;
    }
    return false;
  }

  public void removeAllBoards() {
    boardlist = new ArrayList<>();
  }

  public List<String> getPlayerNameList() {
    List<String> usernameList = new ArrayList<String>();
    for (Board board : boardlist) {
      usernameList.add(board.getPlayer().getName());
    }
    return usernameList;
  }
}
