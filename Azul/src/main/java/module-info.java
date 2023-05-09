module com.example.azul {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.example.azul to javafx.fxml;
  exports com.example.azul;
}