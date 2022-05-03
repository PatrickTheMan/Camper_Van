package com.example.camper_van;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    public static Stage stage;

    public static double minWidth = 1350;
    public static double minHeight = 700;

    public static void run(){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        // Make a DB connection
        //DBConnection.getInstance();

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
            // Close DB connection on program close
            //DBConnection.getInstance().close();
            //Change to SQLExeption
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void setScene(Parent parent){
        stage.setScene(new Scene(parent,minWidth,minHeight));
    }

}

class Controller {

    public static TextField searchPhoneNumber(ArrayList<Customer> customers, TextField textField){

        System.out.println("Functions been Added");

        textField.setOnKeyTyped(actionEvent -> {

            if (textField.getText().length()==8){

                Customer customer = null;

                System.out.println(CustomerList.getInstance().getCustomers());

                // Search for the customer
                for (Customer c:
                        customers) {

                    // Has been found
                    if (c.getPhoneNum().equals(textField.getText())){
                        customer = c;
                        break;
                    }
                }

                if (customer == null){
                    System.out.println("Customer doesn't exist");
                    textField.setText("");
                    PopUp.popText("User Not Found", "#FF6961", "25", App.stage);
                    return;
                } else {

                    PopUp.popText("User Found", "White", "25", App.stage);
                    Scenehandler.getInstance().getMainScene(customer);

                }
            }

        });

        return textField;
    }

    public static Button createNewReservation(Button button){

        button.setOnAction(actionEvent -> {



        });

        return button;
    }

}

class Calculate {

    public static double camperPrice(Camper camper, Customer customer){

        double price=0;

        switch (camper.getPriceCategory()){
            case(1):
                price=100;
                break;
            case(2):
                price=1000;
                break;
            case(3):
                price=10000;
                break;
        }

        if (customer.getReservations().size()>5){// Get the amount of tripes
            price = price*0.9;
        } else if (customer.getReservations().size()>2){
            price = price*0.95;
        }


        return price;
    }

}
