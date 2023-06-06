package pppp.group14project.controller;

import javafx.scene.control.Button;
import pppp.group14project.model.Factory;
import pppp.group14project.model.Tile;

public class ClickableTile extends Button {

    private Factory location;
    private Tile colour;

    public Factory getLocation() {return location;}
    public Tile getColour() {return colour;}

    public void setLocation(Factory location) {this.location = location;}
    public void setColour(Tile colour) {this.colour = colour;}
}
