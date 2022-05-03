package com.example.camper_van;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class Scenehandler {

    private static Scenehandler scenehandler;

    /**
     *  Get the scenehandler instance
     * @return the instance
     */
    public static Scenehandler getInstance()
    {
        if (scenehandler == null) {
            scenehandler = new Scenehandler();
        }

        return scenehandler;
    }

    public void getMainScene(Customer customer){

        // Set the root
        HBox root = new HBox();

        // Split the screen
        VBox leftSide = new VBox();
        leftSide.setMinWidth(1000);
        VBox rightSide = new VBox();
        rightSide.setMinWidth(350);

        // Set the top of the border bane to the search function setup
        leftSide.getChildren().addAll(getOverviewList(10,Fleet.getInstance().getCampers()));

        if (customer == null){
            // Set the right side of the screen
            rightSide.getChildren().addAll(getCustomerNodeSetup(350));
        } else {
            // Set the right side of the screen
            rightSide.getChildren().addAll(getCustomerNodeSetup(350,customer));
        }



        // Add the sides
        root.getChildren().addAll(leftSide,rightSide);



        rightSide.setAlignment(Pos.TOP_CENTER);
        leftSide.setAlignment(Pos.TOP_CENTER);

        App.setScene(root);
    }

    private static Parent getCustomerNodeSetup(int width){

        VBox root = new VBox();

        TextField phoneNumField = new TextField();

        TableView contentView = new TableView();



        phoneNumField.setMinWidth(width);
        phoneNumField.setPromptText("PhoneNumber");
        phoneNumField.setAlignment(Pos.CENTER);

        Controller.searchPhoneNumber(CustomerList.getInstance().getCustomers(),phoneNumField);


        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label();
        nameLabel.setText("Name: None");
        nameLabel.setStyle("-fx-font-size: 20");

        Label phoneNumLabel = new Label();
        phoneNumLabel.setText("PhoneNum: None");
        phoneNumLabel.setStyle("-fx-font-size: 20");

        Label addressLabel = new Label();
        addressLabel.setText("Address: None");
        addressLabel.setStyle("-fx-font-size: 20");

        Label eMailLabel = new Label();
        eMailLabel.setText("E-mail: none");
        eMailLabel.setStyle("-fx-font-size: 20");

        infoBox.getChildren().addAll(nameLabel,phoneNumLabel,addressLabel,eMailLabel);



        HBox tableInfoTexts = new HBox();
        tableInfoTexts.setAlignment(Pos.CENTER);

        Label tripsDiscountLabel = new Label();
        tripsDiscountLabel.setText("Trips: None"+"          "+"Discount: None");
        tripsDiscountLabel.setStyle("-fx-font-size: 15");

        tableInfoTexts.getChildren().addAll(tripsDiscountLabel);



        root.getChildren().addAll(phoneNumField,seperatorH(10),infoBox,seperatorH(10),contentView,seperatorH(10),tableInfoTexts);

        return root;
    }

    private static Parent getCustomerNodeSetup(int width,Customer customer){

        VBox root = new VBox();

        TextField phoneNumField = new TextField();

        TableView contentView = new TableView();



        phoneNumField.setMinWidth(width);
        phoneNumField.setPromptText("PhoneNumber");
        phoneNumField.setAlignment(Pos.CENTER);

        Controller.searchPhoneNumber(CustomerList.getInstance().getCustomers(),phoneNumField);


        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label();
        nameLabel.setText("Name: "+customer.getFirstName()+" "+customer.getLastName());
        nameLabel.setStyle("-fx-font-size: 20");

        Label phoneNumLabel = new Label();
        phoneNumLabel.setText("PhoneNum: "+customer.getPhoneNum());
        phoneNumLabel.setStyle("-fx-font-size: 20");

        Label addressLabel = new Label();
        addressLabel.setText("Address: "+customer.getAddress());
        addressLabel.setStyle("-fx-font-size: 20");

        Label eMailLabel = new Label();
        eMailLabel.setText("E-mail: "+customer.geteMail());
        eMailLabel.setStyle("-fx-font-size: 20");

        infoBox.getChildren().addAll(nameLabel,phoneNumLabel,addressLabel,eMailLabel);



        // The tableCollum with the rental start
        TableColumn<Reservation, Integer> startWeekCol = new TableColumn<>("StartWeek");
        startWeekCol.setCellValueFactory(new PropertyValueFactory<>("rentalStart"));

        // The tableCollum with the rental week amount
        TableColumn<Reservation, Integer> amountWeeksCol = new TableColumn<>("AmountWeeks");
        amountWeeksCol.setCellValueFactory(new PropertyValueFactory<>("rentalWeeks"));

        // The tableCollum with the vehicle
        TableColumn<Reservation, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

        // The tableCollum with the insurance
        TableColumn<Reservation, String> insurancePackageCol = new TableColumn<>("Insurance");
        insurancePackageCol.setCellValueFactory(new PropertyValueFactory<>("insurancePackage"));

        // Format and add the collums to the table
        startWeekCol.setPrefWidth(80);
        amountWeeksCol.setPrefWidth(80);
        vehicleCol.setPrefWidth(105);
        insurancePackageCol.setPrefWidth(80);
        startWeekCol.setSortType(TableColumn.SortType.DESCENDING);
        contentView.getColumns().addAll(startWeekCol, amountWeeksCol, vehicleCol, insurancePackageCol);


        int reservationAmount = 0;

        // Load the reservations and show them in the tableview
        for (Reservation r:
             customer.getReservations()) {
            contentView.getItems().addAll(r);
            reservationAmount++;
        }

        HBox tableInfoTexts = new HBox();
        tableInfoTexts.setAlignment(Pos.CENTER);

        // Setup the discount
        int discountAmount = 0;

        if (reservationAmount>5){
             discountAmount = 10;
        } else if (reservationAmount>2){
            discountAmount = 5;
        }

        Label tripsDiscountLabel = new Label();
        tripsDiscountLabel.setText("Trips: "+reservationAmount+"          "+"Discount: "+discountAmount+"%");
        tripsDiscountLabel.setStyle("-fx-font-size: 15");

        tableInfoTexts.getChildren().addAll(tripsDiscountLabel);



        root.getChildren().addAll(phoneNumField,seperatorH(10),infoBox,seperatorH(10),contentView,seperatorH(10),tableInfoTexts);

        return root;
    }

    private static Parent getOverviewList(int xTranslate, ArrayList<Camper> fleet){

        ScrollPane root = new ScrollPane();
        GridPane content = new GridPane();

        content.setVgap(20);

        content.setAlignment(Pos.CENTER);

        content.addColumn(0,seperatorH(10));
        for (int i = 1; i <= fleet.size()-1 ; i++) {
            content.addColumn(0,getOverviewLine(10,i,fleet.get(i),52));
        }
        content.addColumn(0,seperatorH(10));

        content.setTranslateX(xTranslate);

        root.setContent(content);

        return root;
    }

    private static Parent getOverviewLine(int xTranslate,int camperNum,Camper camper, int amountOfWeeks){

        HBox root = new HBox();

        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();

        Button camperNumButton = hoverOverButton(camper.getLicensePlate());

        GridPane overview = new GridPane();
        overview.setTranslateX(xTranslate);
        overview.setHgap(2);
        overview.setVgap(2);


        int repeat=0;

        for (int i = 1; i <= amountOfWeeks; i++) {

            if (weeksOccupied.contains(i)){
                overview.addColumn(i-(repeat*amountOfWeeks/2),standardButton(i,true));
            } else {
                overview.addColumn(i-(repeat*amountOfWeeks/2),standardButton(i,false));
            }

            if (i%(amountOfWeeks/2) == 0 && i!=0){
                repeat++;
            }

        }

        if (camperNum>9){
            overview.setTranslateX(5);
        }

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(camperNumButton,overview);

        return root;
    }

    private static Separator seperatorH(int height){

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPrefHeight(height);

        return separator;
    }

    private static Separator seperatorV(int width){

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefWidth(width);

        return separator;
    }

    private static Button standardButton(int numberOnWeek, boolean occupied){

        Button standardButton = new Button();
        standardButton.setText(""+numberOnWeek);
        standardButton.setMinSize(30,30);

        if (occupied){
            standardButton.setStyle("-fx-background-color: #FF9997");
            standardButton.setDisable(true);
        } else {
            standardButton.setStyle("-fx-background-color: #CAEEC2");
        }

        return standardButton;
    }

    private static Button hoverOverButton(int numberOnCamper){

        Button hoverOverButton = new Button();
        hoverOverButton.setText("Camper "+numberOnCamper+":");
        hoverOverButton.setStyle("-fx-font-size: 15; -fx-background-color: NULL");
        hoverOverButton.setMinSize(75,60);

        return hoverOverButton;
    }

    private static Button hoverOverButton(String text){

        Button hoverOverButton = new Button();
        hoverOverButton.setText(text+":");
        hoverOverButton.setStyle("-fx-font-size: 15; -fx-background-color: NULL");
        hoverOverButton.setMinSize(75,60);

        return hoverOverButton;
    }

}