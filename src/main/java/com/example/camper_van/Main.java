package com.example.camper_van;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main{

    /*
    App -> choose PhoneNum -> see the history -> make new reservation
    choose week amount -> choose start week -> choose champer
     */

    public static void main(String[] args) {

        CustomerList.getInstance().getCustomers().get(0).getReservations().get(0);

        App.run();


    }
}
