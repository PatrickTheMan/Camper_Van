module com.example.camper_van {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;


    opens com.example.camper_van to javafx.fxml;
    exports com.example.camper_van;
    exports Application;
    opens Application to javafx.fxml;
    exports UI;
    opens UI to javafx.fxml;
    exports Foundation;
    opens Foundation to javafx.fxml;
    exports Domain;
    opens Domain to javafx.fxml;
}