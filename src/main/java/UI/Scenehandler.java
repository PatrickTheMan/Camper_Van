package UI;

import Application.Controller;
import Business.Calculate;
import Domain.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Application.Controller.selectedCustomer;
import static UI.ParentNodeFactory.getCustomerNodeSetup;
import static UI.ParentNodeFactory.getOverviewList;


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

    /**
     * Gets the nodesetup for the main screen
     * @param customer which info will be shown
     */
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

        // Add Alignment
        rightSide.setAlignment(Pos.TOP_CENTER);
        leftSide.setAlignment(Pos.TOP_CENTER);

        // Set the scene of the Application to the mainScene
        App.setScene(root);
    }

    public Stage dialog;
    public TextField textFieldPhone;
    public TextField textFieldWeek;
    public TextField textFieldMail;
    public TextField textFieldName;
    public TextField textFieldLicensePlate;
    public Button buttonSaveAndExit;
    public Label labelPrice;

    /**
     * Get the node setup and create a new stage for the reservation dialogbox
     * @param camper that is reservated
     * @param weekStart the week start
     */
    public void makeReservationStage(Camper camper, int weekStart) {

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
        textFieldWeek.setText(""+weekStart);
        textFieldWeek.setMaxWidth(Double.MAX_VALUE);
        textFieldWeek.setDisable(true);
        gridPane.add(textFieldWeek, 1, 3);

        // The amount of weeks you want combobox container
        ComboBox choiceWeekAmount = new ComboBox();
        choiceWeekAmount.setValue(1);
        Controller.calNewPrice(choiceWeekAmount, camper, weekStart, labelPrice);
        ArrayList<Integer> weeksOccupied = camper.getWeeksOccupied();
        for (int i = weekStart; i <= 52; i++) {

            if (weeksOccupied.contains(i)) {
                break;
            }

            choiceWeekAmount.getItems().add(i - weekStart + 1);

        }
        choiceWeekAmount.setVisibleRowCount(5);
        gridPane.add(choiceWeekAmount, 1, 4);

        // Get the price for 1 week and show it
        labelPrice.setText("Price: "+Calculate.camperPrice(camper,
                selectedCustomer,
                Integer.parseInt(choiceWeekAmount.getValue().toString()),
                weekStart
        ));

        textFieldLicensePlate = new TextField();
        textFieldLicensePlate.setText(camper.getLicensePlate());
        textFieldLicensePlate.setMaxWidth(Double.MAX_VALUE);
        textFieldLicensePlate.setDisable(true);
        gridPane.add(textFieldLicensePlate, 1, 5);

        // Add functionality
        Controller.searchPhoneNumberDialog(textFieldPhone,choiceWeekAmount,camper,weekStart,labelPrice);

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
        Controller.saveAndExitReservation(buttonSaveAndExit,weekStart,choiceWeekAmount);

        // Add cancel button
        Button buttonExit = new Button("Cancel");
        hBoxSaveAndExit.getChildren().add(buttonExit);
        Controller.exitDialog(buttonExit);

        // If customer known then everything gets filled out
        if (selectedCustomer != null) {
            textFieldName.setText(selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
            textFieldName.setDisable(true);
            textFieldMail.setText(selectedCustomer.geteMail());
            textFieldMail.setDisable(true);
            textFieldPhone.setText(selectedCustomer.getPhoneNum());
        }


        // Initialize Window
        dialog.setScene(dialogScene);
        dialog.show();
    }

}