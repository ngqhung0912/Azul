package pppp.group14project.controller;

import javafx.scene.control.Button;
import pppp.group14project.model.Tile;

public class ClickableTile extends Button {

    private Tile colour;

    public Tile getColour() {return colour;}

    public void setColour(Tile colour) {this.colour = colour;}

    public void removeColour(){this.colour = null;}
}
