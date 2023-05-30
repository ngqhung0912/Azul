package pppp.group14project.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Tile;

import java.net.URL;
import java.util.ResourceBundle;

public class PatternController implements Initializable {

    @FXML
    private VBox rows;

    private Button getButton(int rowNumber, int indexNumber) throws InvalidPositionException {
        Button b;
        try {
            HBox r = (HBox) rows.getChildren().get(rowNumber);
            b = (Button) r.getChildren().get(indexNumber);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidPositionException();
        }
        return b;
    }

    private boolean isTileColor(int rowNumber, int indexNumber, Tile tileColor) throws InvalidPositionException {
        System.out.println(getButton(rowNumber, indexNumber).getStyleClass());
        return getButton(rowNumber, indexNumber).getStyleClass().contains(String.valueOf(tileColor));
    }

    private boolean isTileColor(int rowNumber, int indexNumber) throws InvalidPositionException {
        return getButton(rowNumber, indexNumber).getStyleClass().contains("is-colored");
    }


    private void setTiles(int rowNumber, int numberOfTiles, Tile tileColor) throws InvalidPositionException {
        for (int i = 0; i < numberOfTiles; i++) {
            HBox r = (HBox) rows.getChildren().get(rowNumber);
            final int maxNumberOfTiles = r.getChildren().size();
            Button b = getButton(rowNumber, maxNumberOfTiles - 1 - i);
            b.getStyleClass().add(String.valueOf(tileColor));
            b.getStyleClass().add("is-colored");
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button b = getButton(0, 0);
        // You can't use getStyle if the element uses style from a stylesheet
//        b.setStyle("-fx-background-color: blue;");
//        b.getStyleClass().add("tile-red");

        System.out.println(isTileColor(4, 3, Tile.BLUE));

        setTiles(4, 4, Tile.BLUE);

        System.out.println(isTileColor(4, 3, Tile.BLUE));

    }
}

