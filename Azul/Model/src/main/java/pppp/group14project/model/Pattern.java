package pppp.group14project.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

    @Getter
    ObservableList<PatternLine> patternLines = FXCollections.observableArrayList(
            item -> new Observable[] { item.getSpaces() }
    );

    public Pattern() {
        // Adds 5 PatternLines
        for (int i = 0; i < 5; i++) {
            patternLines.add(new PatternLine(i + 1));
        }
    }

    /**
     * Adds tiles to a PatternLine, returns the excess tiles to add to the floor
     * @param patternLineNumber the PatternLine to add tiles to
     * @param tiles the tiles to add
     * @return the excess tiles, or an empty list if none
     */
    public List<Tile> addTiles(int patternLineNumber, List<Tile> tiles) throws WrongTileException {
        PatternLine line = patternLines.get(patternLineNumber);
        List<Tile> returnedTiles = line.addTiles(tiles);
        return returnedTiles;
    }

}
