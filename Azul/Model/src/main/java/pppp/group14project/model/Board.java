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
    @Setter
    private Player player;

    public Board(Player player) {
        this.player = player;
    }

    public void placeOnWall() {}

    public void destroyAll() {}
}
