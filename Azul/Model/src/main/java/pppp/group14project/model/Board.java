package pppp.group14project.model;

import javafx.collections.ObservableList;
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
    @Setter
    private int score;

    @Getter
    @Setter
    private ArrayList<Tile> selected_tiles;

    public void placeOnWall() {}

    public void destroyAll() {}


  public Board(Player player) {
    this.player = player;
    this.score = 0;
    this.floor = new Floor();
    this.pattern = new Pattern();
    this.wall = new Wall();
  }
}
