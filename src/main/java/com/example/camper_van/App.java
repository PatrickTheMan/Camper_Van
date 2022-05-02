package com.example.camper_van;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    public static void run(){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new Pane(), 1200, 600);
        stage.setTitle("Camper Van");
        stage.setScene(scene);
        stage.show();
    }

    private static void mainSetupOfWindow(){

        

    }

}
