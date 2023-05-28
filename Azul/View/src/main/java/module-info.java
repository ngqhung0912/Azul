module pppp.group14project.view {
    requires javafx.controls;
    requires javafx.graphics;

    requires javafx.fxml;


    opens pppp.group14project.view to javafx.fxml;
    exports pppp.group14project.view;
}