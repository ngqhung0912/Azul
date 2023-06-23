package pppp.group14project.model;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Getter
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
