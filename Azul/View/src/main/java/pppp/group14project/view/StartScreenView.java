package pppp.group14project.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartScreenView.class.getResource("/start-screen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(1250);
        stage.setMaxWidth(1250);
        stage.setMinHeight(700);
        stage.setMinHeight(700);
        stage.setTitle("Azul is the best game ever!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}