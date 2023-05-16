package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public static void main(String[] args) {
        Player player = new Player("Hung");
        System.out.println(player.getName());
    }
}
