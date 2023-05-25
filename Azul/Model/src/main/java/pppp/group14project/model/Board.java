package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

public class Board {

    @Getter
    @Setter
    private Wall wall;

    @Getter
    @Setter
    private Pattern pattern;
    @Getter
    @Setter
    private Floor floor;
    @Getter
    private Player player;
    @Getter
    @Setter
    private int score;

    public Board(Player player) {
        this.player = player;
    }

    public void placeOnWall() {}

    public void destroyAll() {}


  public Board(Player player) {
    this.player = player;
    this.score = 0;
  }
}
