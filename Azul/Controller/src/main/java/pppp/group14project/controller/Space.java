package pppp.group14project.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;

public class Space extends Button {

    private SimpleIntegerProperty row = new SimpleIntegerProperty();
    private SimpleIntegerProperty index = new SimpleIntegerProperty();

    public int getRow() {
        return row.get();
    }

    public SimpleIntegerProperty rowProperty() {
        return row;
    }

    public void setRow(int row) {
        this.row.set(row);
    }

    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }
}
