package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

public class Board {
  @Getter
  private Player player;

  @Getter
  @Setter
  private int score;

  @Getter
  private Floor floor;

  public Board(Player player) {
    this.player = player;
    this.score = 0;
    this.floor = new Floor();
  }
}
