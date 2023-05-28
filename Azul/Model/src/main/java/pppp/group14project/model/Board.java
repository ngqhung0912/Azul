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

    public void placeOnWall() {}

    public void destroyAll() {}


  @Getter
  private Floor floor;

  public Board(Player player) {
    this.player = player;
    this.score = 0;
    this.floor = new Floor();
  }
}
