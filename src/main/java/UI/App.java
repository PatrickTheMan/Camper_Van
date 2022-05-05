package UI;

import Foundation.DBConnection;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class App extends Application {

    public static Stage stage;

    public static final double minWidth = 1350;
    public static final double minHeight = 700;

    public static void run(){
        launch();
    }

    @Override
    public void start(Stage stage) {

        // Starting procedures and Set the Scene
        this.stage = stage;

        // Initialize Window
        stage.setResizable(false);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        Scenehandler.getInstance().getMainScene(null);
        stage.setTitle("Campers Vans Rental");
        stage.show();
    }

    @Override
    public void stop() {
        try {
            if (DBConnection.getInstance()!=null){
                DBConnection.getInstance().close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void setScene(Parent parent){
        stage.setScene(new Scene(parent,minWidth,minHeight));
    }

}

