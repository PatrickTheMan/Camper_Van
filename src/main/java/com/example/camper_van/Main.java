package com.example.camper_van;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main{

    /*
    App -> choose PhoneNum -> see the history -> make new reservation
    choose week amount -> choose start week -> choose champer
     */

    public static void main(String[] args) {
        App.run();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {

            }
        }, "Shutdown-thread"));
    }
}
