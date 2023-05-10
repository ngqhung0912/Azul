module pppp.group14project.azul {
  requires javafx.controls;
  requires javafx.fxml;
  requires static lombok;


  opens pppp.group14project.azul to javafx.fxml;
  exports pppp.group14project.azul;
}