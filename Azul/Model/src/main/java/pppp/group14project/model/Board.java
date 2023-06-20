package pppp.group14project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
    private IntegerProperty score;

    @Getter
    @Setter
    private ArrayList<Tile> selected_tiles;

    public void placeOnWall() {}

    public void destroyAll() {}


  public Board(Player player) {
    this.player = player;
    this.score = new SimpleIntegerProperty(0);
    this.floor = new Floor();
    this.pattern = new Pattern();
    this.wall = new Wall();
  }

  public void updateScore(int score) {
    this.score.set(score);
  }

  public void useTeam13Wall() {
    this.wall = new WallAdapter();
  }
}
