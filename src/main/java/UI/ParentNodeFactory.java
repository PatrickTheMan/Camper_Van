package UI;

import Application.Controller;
import Domain.Camper;
import Domain.Customer;
import Domain.Reservation;
import Domain.Season;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.ArrayList;

import static Application.Controller.selectedCustomer;


public class ParentNodeFactory {

    private static ParentNodeFactory parentNodeFactory;

    /**
     *  Get the scenehandler instance
     * @return the instance
     */
    public static ParentNodeFactory getInstance()
    {
        if (parentNodeFactory == null) {
            parentNodeFactory = new ParentNodeFactory();
        }

        return parentNodeFactory;
    }

    /**
     * Makes a parent with the customerNodeSetup - no customer selected
     * @param width of the search TextField
     * @return the parent setup
     */
    public static Parent getCustomerNodeSetup(int width){

        VBox root = new VBox();

        TextField phoneNumField = new TextField();

        TableView contentView = new TableView();

        phoneNumField.setMinWidth(width);
        phoneNumField.setPromptText("PhoneNumber");
        phoneNumField.setAlignment(Pos.CENTER);

        Controller.searchPhoneNumber(phoneNumField);


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



        root.getChildren().addAll(phoneNumField,
                ParentNodeFactory.getInstance().seperatorH(10),infoBox,
                ParentNodeFactory.getInstance().seperatorH(10),contentView,
                ParentNodeFactory.getInstance().seperatorH(10),tableInfoTexts);

        return root;
    }

    /**
     * Makes a parent with the customerNodeSetup - no customer selected
     * @param width of the search TextField
     * @param customer that is selected
     * @return the parent setup
     */
    public static Parent getCustomerNodeSetup(int width, Customer customer){

        VBox root = new VBox();

        TextField phoneNumField = new TextField();

        TableView contentView = new TableView();

        phoneNumField.setMinWidth(width);
        phoneNumField.setPromptText("PhoneNumber");
        phoneNumField.setAlignment(Pos.CENTER);

        // Search for the customers number
        Controller.searchPhoneNumber(phoneNumField);

        // Set the selected customer
        selectedCustomer = customer;

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
        startWeekCol.setSortType(TableColumn.SortType.ASCENDING);
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

        // The tableView uses the rule from before
        contentView.getSortOrder().addAll(startWeekCol);

        root.getChildren().addAll(phoneNumField,
                ParentNodeFactory.getInstance().seperatorH(10),infoBox,
                ParentNodeFactory.getInstance().seperatorH(10),contentView,
                ParentNodeFactory.getInstance().seperatorH(10),tableInfoTexts);

        return root;
    }

    /**
     * Makes a parent an overviewListNodeSetup, which includes all the overviewlines
     * @param xTranslate for adjusting x if needed
     * @param fleet that is active
     * @return the parent
     */
    public static Parent getOverviewList(int xTranslate, ArrayList<Camper> fleet){

        ScrollPane root = new ScrollPane();
        GridPane content = new GridPane();

        content.setVgap(20);

        content.setAlignment(Pos.CENTER);

        content.addColumn(0,ParentNodeFactory.getInstance().seperatorH(10));
        for (int i = 0; i <= fleet.size()-1 ; i++) {
            content.addColumn(0,getOverviewLine(0,fleet.get(i),52));
        }
        content.addColumn(0,ParentNodeFactory.getInstance().seperatorH(10));

        content.setTranslateX(xTranslate);

        root.setContent(content);

        return root;
    }

    /**
     * Makes a parent of the overviewLineNodeSetup
     * @param xTranslate
     * @param camper
     * @param amountOfWeeks
     * @return
     */
    public static Parent getOverviewLine(int xTranslate,Camper camper, int amountOfWeeks){

        HBox root = new HBox();

        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();
        ArrayList<Integer> weeksSeason = Season.getInstance().getSeason();

        Button camperNumButton = ParentNodeFactory.getInstance().hoverOverButton(camper.getLicensePlate()+":",camper);

        GridPane overview = new GridPane();
        overview.setTranslateX(xTranslate);
        overview.setHgap(2);
        overview.setVgap(2);


        int repeat=0;

        for (int i = 1; i <= amountOfWeeks; i++) {

            if (weeksOccupied.contains(i)){
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        ParentNodeFactory.getInstance().standardButton(i,true,false,camper));
            } else if (weeksSeason.contains(i)) {
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        ParentNodeFactory.getInstance().standardButton(i,false,true,camper));
            } else {
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        ParentNodeFactory.getInstance().standardButton(i,false,false,camper));

            }

            if (i%(amountOfWeeks/2) == 0 && i!=0){
                repeat++;
            }

        }

        // Make it liner
        if (camper.getPriceCategory().equals("Basic")){
            overview.setTranslateX(3);
        } else if (camper.getPriceCategory().equals("Luxury")){
            overview.setTranslateX(3);
        }

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(camperNumButton,overview);

        return root;
    }

    /**
     * Makes a separator on the horizontal axis
     * @param height of the separator
     * @return the separator
     */
    public Separator seperatorH(int height){

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPrefHeight(height);

        return separator;
    }

    /**
     * Makes a separator on the vertical axis
     * @param width of the separator
     * @return the separator
     */
    public Separator seperatorV(int width){

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefWidth(width);

        return separator;
    }

    /**
     * Makes a button for the weeks schedule
     * @param numberOnWeek the text on the button
     * @param occupied whether to be red
     * @param seasonWeek whether to be yellow
     * @param camper the camper associated with the button
     * @return the button
     */
    public Button standardButton(int numberOnWeek, boolean occupied, boolean seasonWeek, Camper camper){

        Button standardButton = new Button();
        standardButton.setText(""+numberOnWeek);
        standardButton.setMinSize(30,30);

        if (occupied){
            standardButton.setStyle("-fx-background-color: #FF9997");
            standardButton.setDisable(true);
        } else if (seasonWeek){
            standardButton.setStyle("-fx-background-color: #FDF98B");
        } else {
            standardButton.setStyle("-fx-background-color: #CAEEC2");
        }

        // Add function to the button
        Controller.createNewReservation(standardButton,camper,numberOnWeek);

        return standardButton;
    }

    /**
     * Makes a button and returns it
     * @param text on the button
     * @return the button made
     */
    public Button hoverOverButton(String text, Camper camper){

        Button hoverOverButton = new Button();
        hoverOverButton.setText(text);
        hoverOverButton.setStyle("-fx-font-size: 15; -fx-background-color: NULL");
        hoverOverButton.setMinSize(75,60);

        // Custom popup which differentiates from the others which is why we don't use the class
        Popup popup = new Popup();

        Label label = new Label(camper.getLicensePlate() + "\n\n" +
                "Category: \t" + camper.getPriceCategory() + "\n" +
                "Price: \t\t" + camper.getPrice() + "\n" +
                "Brand: \t\t" + camper.getBrand() + "\n" +
                "Model: \t\t" + camper.getModel() + "\n" +
                "Area: \t\t" + camper.getArea() + "\n" +
                "Seats: \t\t" + camper.getSeats() + "\n" +
                "Weight: \t\t" + camper.getWeight());

        label.setStyle("-fx-background-color: white;"
                + " -fx-text-fill: Black;"
                + " -fx-font-size: 18;"
                + " -fx-padding: 10px;"
                + " -fx-background-radius: 6;");

        popup.getContent().add(label);

        hoverOverButton.setOnMouseEntered(mouseEvent -> {
            popup.show(App.stage);

        });

        hoverOverButton.setOnMouseExited(mouseEvent -> popup.hide());

        return hoverOverButton;
    }

}
