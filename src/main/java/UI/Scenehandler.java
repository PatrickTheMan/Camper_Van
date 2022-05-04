package UI;

import Application.Controller;
import Business.Calculate;
import Domain.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Application.Controller.selectedCustomer;


public class Scenehandler {

    // Needs TO BE FIXED
    private static Label nameLabel;
    private static Label phoneNumLabel;
    private static Label addressLabel;
    private static Label eMailLabel;


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
        leftSide.getChildren().addAll(getOverviewList(10, Fleet.getInstance().getCampers()));

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

        nameLabel = new Label();
        nameLabel.setText("Name: None");
        nameLabel.setStyle("-fx-font-size: 20");

        phoneNumLabel = new Label();
        phoneNumLabel.setText("PhoneNum: None");
        phoneNumLabel.setStyle("-fx-font-size: 20");

        addressLabel = new Label();
        addressLabel.setText("Address: None");
        addressLabel.setStyle("-fx-font-size: 20");

        eMailLabel = new Label();
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
                Scenehandler.getInstance().seperatorH(10),infoBox,
                Scenehandler.getInstance().seperatorH(10),contentView,
                Scenehandler.getInstance().seperatorH(10),tableInfoTexts);

        return root;
    }

    private static Parent getCustomerNodeSetup(int width,Customer customer){

        VBox root = new VBox();

        TextField phoneNumField = new TextField();

        TableView contentView = new TableView();



        phoneNumField.setMinWidth(width);
        phoneNumField.setPromptText("PhoneNumber");
        phoneNumField.setAlignment(Pos.CENTER);

        // Search for the customers number
        Controller.searchPhoneNumber(CustomerList.getInstance().getCustomers(),phoneNumField);

        // Set the selected customer
        selectedCustomer = customer;


        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);

        nameLabel = new Label();
        nameLabel.setText("Name: "+customer.getFirstName()+" "+customer.getLastName());
        nameLabel.setStyle("-fx-font-size: 20");

        phoneNumLabel = new Label();
        phoneNumLabel.setText("PhoneNum: "+customer.getPhoneNum());
        phoneNumLabel.setStyle("-fx-font-size: 20");

        addressLabel = new Label();
        addressLabel.setText("Address: "+customer.getAddress());
        addressLabel.setStyle("-fx-font-size: 20");

        eMailLabel = new Label();
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
                Scenehandler.getInstance().seperatorH(10),infoBox,
                Scenehandler.getInstance().seperatorH(10),contentView,
                Scenehandler.getInstance().seperatorH(10),tableInfoTexts);

        return root;
    }

    private static Parent getOverviewList(int xTranslate, ArrayList<Camper> fleet){

        ScrollPane root = new ScrollPane();
        GridPane content = new GridPane();

        content.setVgap(20);

        content.setAlignment(Pos.CENTER);

        content.addColumn(0,Scenehandler.getInstance().seperatorH(10));
        for (int i = 0; i <= fleet.size()-1 ; i++) {
            content.addColumn(0,getOverviewLine(0,fleet.get(i),52));
        }
        content.addColumn(0,Scenehandler.getInstance().seperatorH(10));

        content.setTranslateX(xTranslate);

        root.setContent(content);

        return root;
    }

    private static Parent getOverviewLine(int xTranslate,Camper camper, int amountOfWeeks){

        HBox root = new HBox();

        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();
        ArrayList<Integer> weeksSeason = Season.getInstance().getSeason();

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

        Button camperNumButton = Scenehandler.getInstance().hoverOverButton(camper.getLicensePlate()+":");
        camperNumButton.setOnMouseEntered(mouseEvent -> {
            popup.show(App.stage);

        });

        camperNumButton.setOnMouseExited(mouseEvent -> popup.hide());

        GridPane overview = new GridPane();
        overview.setTranslateX(xTranslate);
        overview.setHgap(2);
        overview.setVgap(2);


        int repeat=0;

        for (int i = 1; i <= amountOfWeeks; i++) {

            if (weeksOccupied.contains(i)){
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        Scenehandler.getInstance().standardButton(i,true,false,camper));
            } else if (weeksSeason.contains(i)) {
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        Scenehandler.getInstance().standardButton(i,false,true,camper));
            } else {
                overview.addColumn(i-(repeat*amountOfWeeks/2),
                        Scenehandler.getInstance().standardButton(i,false,false,camper));

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

    private Separator seperatorH(int height){

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPrefHeight(height);

        return separator;
    }

    private Separator seperatorV(int width){

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefWidth(width);

        return separator;
    }

    private Button standardButton(int numberOnWeek, boolean occupied, boolean seasonWeek, Camper camper){

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
        Controller.createNewReservation(standardButton,selectedCustomer,camper,""+numberOnWeek);

        return standardButton;
    }

    private Button hoverOverButton(String text){

        Button hoverOverButton = new Button();
        hoverOverButton.setText(text);
        hoverOverButton.setStyle("-fx-font-size: 15; -fx-background-color: NULL");
        hoverOverButton.setMinSize(75,60);

        return hoverOverButton;
    }

    public Stage dialog;
    public TextField textFieldPhone;
    public TextField textFieldWeek;
    public TextField textFieldMail;
    public TextField textFieldName;
    public TextField textFieldLicensePlate;
    public Button buttonSaveAndExit;
    public Label labelPrice;

    public void makeReservationStage(Customer customer, Camper camper, String weekStart) {

        final double dialogWidth = 450;
        final double dialogHeight = 450;


        dialog = new Stage();

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
        gridPane.setPadding(new Insets(0, 0, 0, 10));  // padding 10 left side
        borderPane.setCenter(gridPane);
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        VBox.setMargin(gridPane, new Insets(5, 5, 5, 5));

        // Dimensions of GridPane
        final int numRows = 7;

        // Sets the width of the first column
        ColumnConstraints colConst0 = new ColumnConstraints(150);
        gridPane.getColumnConstraints().add(colConst0);

        // Sets the width of the second column
        ColumnConstraints colConst1 = new ColumnConstraints(250);
        gridPane.getColumnConstraints().add(colConst1);

        // Sets the height of all rows
        for (int i = 0; i < numRows - 1; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            gridPane.getRowConstraints().add(rowConst);
        }
        for (int i = numRows - 1; i < numRows; i++) {
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
        labelPrice = new Label("");
        labelPrice.setStyle("-fx-font-scale: Bold; -fx-font-size: 20");

        // Adding Labels to GridPane
        gridPane.add(labelPhone, 0, 0);
        gridPane.add(labelMail, 0, 1);
        gridPane.add(labelName, 0, 2);
        gridPane.add(labelWeekStart, 0, 3);
        gridPane.add(labelWeekAmount, 0, 4);
        gridPane.add(labelCamper, 0, 5);
        gridPane.add(labelPrice, 0, 6);


        textFieldPhone = new TextField();
        textFieldPhone.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldPhone, 1, 0);

        textFieldMail = new TextField();
        textFieldMail.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldMail, 1, 1);

        textFieldName = new TextField();
        textFieldName.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textFieldName, 1, 2);

        textFieldWeek = new TextField();
        textFieldWeek.setText(weekStart);
        textFieldWeek.setMaxWidth(Double.MAX_VALUE);
        textFieldWeek.setDisable(true);
        gridPane.add(textFieldWeek, 1, 3);

        // The amount of weeks you want combobox container
        ComboBox choiceWeekAmount = new ComboBox();
        choiceWeekAmount.setValue(1);
        Controller.calNewPrice(choiceWeekAmount, camper, customer, Integer.parseInt(weekStart), labelPrice);
        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();
        for (int i = Integer.parseInt(weekStart); i <= 52; i++) {

            if (weeksOccupied.contains(i)) {
                break;
            }

            choiceWeekAmount.getItems().add(i - Integer.parseInt(weekStart) + 1);

        }
        choiceWeekAmount.setVisibleRowCount(5);
        gridPane.add(choiceWeekAmount, 1, 4);

        // Get the price for 1 week and show it
        labelPrice.setText("Price: "+Calculate.camperPrice(camper,
                customer,
                Integer.parseInt(choiceWeekAmount.getValue().toString()),
                Integer.parseInt(weekStart)
        ));

        textFieldLicensePlate = new TextField();
        textFieldLicensePlate.setText(camper.getLicensePlate());
        textFieldLicensePlate.setMaxWidth(Double.MAX_VALUE);
        textFieldLicensePlate.setDisable(true);
        gridPane.add(textFieldLicensePlate, 1, 5);

        // Add functionality
        Controller.searchPhoneNumber(CustomerList.getInstance().getCustomers(), textFieldPhone, camper, weekStart);

        // Add ToolBar to Bottom of Dialog
        ToolBar toolBar = new ToolBar();
        borderPane.setBottom(toolBar);

        // Create HBox for alignment of Buttons
        HBox hBoxSaveAndExit = new HBox();
        toolBar.getItems().add(hBoxSaveAndExit);
        hBoxSaveAndExit.setAlignment(Pos.CENTER);
        hBoxSaveAndExit.setSpacing(8);

        // Add Save Button (saves to DB)
        buttonSaveAndExit = new Button("Save and exit!");
        hBoxSaveAndExit.getChildren().add(buttonSaveAndExit);
        Controller.saveAndExitReservation(buttonSaveAndExit,Integer.parseInt(weekStart),choiceWeekAmount);

        // Add cancel button
        Button buttonExit = new Button("Cancel");
        hBoxSaveAndExit.getChildren().add(buttonExit);
        Controller.exitDialog(buttonExit,dialog);

        // If customer known then everything gets filled out
        if (selectedCustomer != null) {
            customer = selectedCustomer;
            textFieldName.setText(customer.getFirstName() + " " + customer.getLastName());
            textFieldName.setDisable(true);
            textFieldMail.setText(customer.geteMail());
            textFieldMail.setDisable(true);
            textFieldPhone.setText(customer.getPhoneNum());
        }


        // Initialize Window
        dialog.setScene(dialogScene);
        dialog.show();
    }

}