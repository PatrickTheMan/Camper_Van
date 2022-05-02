module com.example.camper_van {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.camper_van to javafx.fxml;
    exports com.example.camper_van;
}