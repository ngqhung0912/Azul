package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private static Game instance = null;
  private int currentRound;

  @Getter
  @Setter
  private Table table;

  @Getter
  private List<Board> boardList = new ArrayList<Board>();

  @Getter
  private List<Factory> factoryList = new ArrayList<>();

  @Getter
  private TileContainer tilecontainer = new TileContainer();

  private int roundTurnCounter;

  @Getter
  @Setter
  private List<Integer> playerOrder;

  public int getNextPlayerID() {
    int numberOfPlayers = boardList.size();
    int playerIndex = roundTurnCounter % numberOfPlayers;
    int playerID = playerOrder.get(playerIndex);
    return playerID;
  }

  public void nextPlayer() {
    roundTurnCounter++;
  }

  public void generateTurns(int startPlayerID) {
    int numberOfPlayers = boardList.size();
    List<Integer> result = new ArrayList<>();

    int currentNumber = startPlayerID;
    for (int i = 0; i < numberOfPlayers; i++) {
      result.add(currentNumber);
      currentNumber = (currentNumber + 1) % numberOfPlayers;
    }

    this.roundTurnCounter = 0;
    this.playerOrder = result;
  }

  public Game() {
    currentRound = 1;
    roundTurnCounter = 0;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }
  
  //TODO (REFACTOR): This method should be throwing an exception if you want to notify that no more board can be added since its full.
  public Boolean addBoard(Board board) {
    if (boardList.size() < 4) {
      boardList.add(board);
      return true;
    }
    return false;
  }

  public List<String> getPlayerNameList() {
    List<String> usernameList = new ArrayList<String>();
    for (Board board : boardList) {
      usernameList.add(board.getPlayer().getName());
    }
    return usernameList;
  }

  public void fillFactories(List<Tile> tiles) {
    Integer counter = 0;
    for (Factory factory: factoryList) {
      try {
        factory.addTiles(tiles.subList(counter, counter+4));
      } catch (FullException e) {
        e.printStackTrace();
      }
      counter = counter+4;
    }
  }
}
