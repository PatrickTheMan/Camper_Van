package com.example.camper_van;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    public static Stage stage;

    public static final double minWidth = 1350;
    public static final double minHeight = 700;

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

    public static TextField searchPhoneNumber(ArrayList<Customer> customers, TextField textField, boolean reservationActive){

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

                    // DO STUFF

                }
            }

        });

        return textField;
    }

    public static Button createNewReservation(Button button,Customer customer, Camper camper, String weekStart){

        button.setOnAction(actionEvent -> {

            System.out.println("Phonenum: "+customer.getPhoneNum());

            if (!customer.getPhoneNum().equals("PhoneNum: None")){
                //System.out.println(phoneNum+" / "+licensePlate+" / "+weekStart);
                makeReservationStage(customer,camper,weekStart);
            }


        });

        return button;
    }

    private static void makeReservationStage(Customer customer, Camper camper, String weekStart){

        final double dialogWidth = 450;
        final double dialogHeight = 450;


        Stage dialog = new Stage();

        dialog.setResizable(false);
        dialog.setTitle("New booking");

        VBox vBoxDialog = new VBox(5);

        Scene dialogScene = new Scene(vBoxDialog, dialogWidth, dialogHeight);

        // Disables the option to use main window when dialog is active
        dialog.initModality(Modality.APPLICATION_MODAL);


        // Add BorderPane
        BorderPane borderPane = new BorderPane();
        VBox.setVgrow(borderPane, Priority.ALWAYS);
        vBoxDialog.getChildren().add(borderPane);

        // Add GridPane to Center
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0,0,0,10));  // padding 10 left side
        borderPane.setCenter(gridPane);
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        VBox.setMargin(gridPane, new Insets(5, 5, 5, 5));

        // Dimensions of GridPane
        final int numRows = 7 ;

        // Sets the width of the first column
        ColumnConstraints colConst0 = new ColumnConstraints(150);
        gridPane.getColumnConstraints().add(colConst0);

        // Sets the width of the second column
        ColumnConstraints colConst1 = new ColumnConstraints(250);
        gridPane.getColumnConstraints().add(colConst1);

        // Sets the height of all rows
        for (int i = 0; i < numRows-1; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            gridPane.getRowConstraints().add(rowConst);
        }
        for (int i = numRows-1; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(80);
            gridPane.getRowConstraints().add(rowConst);
        }

        // Creating Labels for GridPane
        Label labelPhone = new Label("Phone Number:");
        Label labelMail = new Label("E-mail:");
        Label labelName = new Label("Name of Customer:");
        Label labelCamper = new Label("Camper License Plate:");
        Label labelWeekStart = new Label("WeekStart:");
        Label labelWeekAmount = new Label("WeekAmount:");
        Label labelNotes = new Label("Notes:");

        // Adding Labels to GridPane
        gridPane.add(labelPhone, 0, 0);
        gridPane.add(labelMail, 0, 1);
        gridPane.add(labelName, 0, 2);
        gridPane.add(labelWeekStart, 0, 3);
        gridPane.add(labelWeekAmount, 0, 4);
        gridPane.add(labelCamper, 0, 5);
        gridPane.add(labelNotes, 0, 6);


        TextField textFieldPhone = new TextField();
        textFieldPhone.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldPhone, 1, 0);

        TextField textFieldMail = new TextField();
        textFieldMail.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldMail, 1, 1);

        TextField textFieldName = new TextField();
        textFieldName.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldName, 1, 2);

        TextField textFieldWeek = new TextField();
        textFieldWeek.setText(weekStart);
        textFieldWeek.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldWeek, 1, 3);

        // The amount of weeks you want combobox container
        ComboBox choiceWeekAmount = new ComboBox();
        choiceWeekAmount.setValue("1");
        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();
        for (int i = Integer.parseInt(weekStart); i <= 52; i++) {

            if (weeksOccupied.contains(i)){
                break;
            }

            choiceWeekAmount.getItems().add(i-Integer.parseInt(weekStart)+1);

        }
        gridPane.add(choiceWeekAmount, 1, 4);


        TextField textFieldLicensePlate = new TextField(weekStart);
        textFieldLicensePlate.setText(camper.getLicensePlate());
        textFieldLicensePlate.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldLicensePlate, 1, 5);

        TextArea comments = new TextArea();
        comments.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(comments, 1, 6);

        Controller.searchPhoneNumber(CustomerList.getInstance().getCustomers(), textFieldPhone,true);

        // Add ToolBar to Bottom of Dialog
        ToolBar toolBar = new ToolBar();
        borderPane.setBottom(toolBar);

        // Create HBox for alignment of Buttons
        HBox hBoxSaveAndExit = new HBox();
        toolBar.getItems().add(hBoxSaveAndExit);
        hBoxSaveAndExit.setAlignment(Pos.CENTER);
        hBoxSaveAndExit.setSpacing(8);

        // Add Save Button (saves to DB)
        Button buttonSaveAndExit = new Button("Save and exit!");
        hBoxSaveAndExit.getChildren().add(buttonSaveAndExit);

        // On button click, call this method
        buttonSaveAndExit.setOnAction(event -> {

            // MAKE RESERVATION IF EVERYTHING IS FILLED

        });

        // Add cancel button
        Button buttonExit = new Button("Cancel");
        hBoxSaveAndExit.getChildren().add(buttonExit);
        buttonExit.setOnAction(event -> {
            dialog.close();
        });


        // If customer known then everything gets filled out
        if (customer!=null){
            textFieldName.setText(customer.getFirstName()+" "+customer.getLastName());
            textFieldMail.setText(customer.geteMail());
            textFieldPhone.setText(customer.getPhoneNum());
        }





        // Initialize Window
        dialog.setScene(dialogScene);
        dialog.show();
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
