package com.example.camper_van;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    public static void run(){
        launch();
    }

    public static BorderPane borderPane;
    public static Stage stage;
    public static Scene applicationScene;

    public double minWidth = 1150;
    public double minHeight = 600;

    @Override
    public void start(Stage stage) throws IOException {

        // Make a DB connection
        DBConnection.getInstance();

        // Starting procedures and Set the Scene
        this.stage = stage;
        VBox root = new VBox();
        applicationScene = new Scene(root, minWidth, minHeight);

        // Create a borderPane
        borderPane = new BorderPane();
        root.getChildren().add(borderPane);

        // Enables resizing
        VBox.setVgrow(borderPane, Priority.ALWAYS);

        // Fetch Overview
        borderPane.setCenter(Overview.getOverview());

        // Initialize Window
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setScene(applicationScene);
        stage.setTitle("EASV Animal Shelter");
        stage.show();
    }

    @Override
    public void stop() {
        try {
            // Close DB connection on program close
            DBConnection.getInstance().close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void mainSetupOfWindow(){



    }

}
