package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;
import pppp.group14project.model.exceptions.FullException;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private static Game instance = null;

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
    roundTurnCounter = 0;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }
  
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
    int counter = 0;
    for (Factory factory: factoryList) {
      try {
        factory.addTiles(tiles.subList(counter, counter+4));
      } catch (FullException e) {
        e.printStackTrace();
      }
      counter = counter+4;
    }
  }

  public void resetGame(){
    boardList.clear();
    factoryList.clear();
    tilecontainer.reset();

  }
}
