package Application;

import Business.Calculate;
import Domain.Camper;
import Domain.Customer;
import Domain.CustomerList;
import Foundation.DB;
import UI.App;
import UI.PopUp;
import UI.Scenehandler;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Controller {

    // If a customer is selected then that customer will be saved in this variable
    public static Customer selectedCustomer;

    /**
     * This is used by the main screen search textfield to search after a specific customer via phoneNumber
     * @param textField that needs this functionality
     */
    public static void searchPhoneNumber(TextField textField) {

        textField.setOnKeyTyped(actionEvent -> {

            // If the textfield contains 8 chars
            if (textField.getText().length() == 8) {

                // Is is a number?
                try {
                    Integer.parseInt(textField.getText());
                } catch (NumberFormatException e){
                    return;
                }

                // Initiate and declare a customer
                Customer customer = null;

                // Search for the customer
                for (Customer c :
                        CustomerList.getInstance().getCustomers()) {

                    // Has been found
                    if (c.getPhoneNum().equals(textField.getText())) {
                        customer = c;
                        break;
                    }
                }

                // Tell the current program user whether a customer has been found
                if (customer == null) {
                    // The customer hasn't been found
                    textField.setText("");
                    PopUp.popText("User Not Found", "#FF6961", "25", App.stage);
                } else {
                    // The customer has been found
                    PopUp.popText("User Found", "Black", "25", App.stage);
                    selectedCustomer = customer;
                    Scenehandler.getInstance().getMainScene(customer);
                }
            }
        });
    }

    /**
     * This is used by the reservation window search textfield to search after a specific customer via phoneNumber
     * @param textField that needs this functionality
     */
    public static void searchPhoneNumberDialog(TextField textField, ComboBox comboBox, Camper camper, int weekStart, Label priceLabel) {

        textField.setOnKeyTyped(actionEvent -> {

            if (textField.getText().length() == 8) {

                // Is is a number?
                try {
                    Integer.parseInt(textField.getText());
                } catch (NumberFormatException e){
                    return;
                }

                Customer customer = null;

                // Search for the customer
                for (Customer c :
                        CustomerList.getInstance().getCustomers()) {

                    // Has been found
                    if (c.getPhoneNum().equals(textField.getText())) {
                        customer = c;
                        break;
                    }
                }

                // Tell the current program user whether a customer has been found
                if (customer == null) {
                    // The customer hasn't been found
                    PopUp.popText("User Not Found", "#FF6961", "25", Scenehandler.getInstance().dialog);
                    textField.setText("");
                } else {
                    // The customer has been found
                    PopUp.popText("User Found", "Black", "25", Scenehandler.getInstance().dialog);

                    selectedCustomer = customer;

                    Scenehandler.getInstance().textFieldMail.setText(customer.geteMail());
                    Scenehandler.getInstance().textFieldName.setText(customer.getFirstName() + " " + customer.getLastName());

                    Scenehandler.getInstance().textFieldName.setDisable(true);
                    Scenehandler.getInstance().textFieldMail.setDisable(true);

                    Scenehandler.getInstance().buttonSaveAndExit.setDisable(false);

                    // Updates the price
                    priceLabel.setText("Price: " + Calculate.camperPrice(camper, selectedCustomer,
                            Integer.parseInt(comboBox.getValue().toString()),
                            weekStart)
                    );

                }
            } else if (textField.getText().length() != 8 && !Scenehandler.getInstance().textFieldName.getText().equals("")) {
                // The customer gets cleared as the phoneNum gets changed

                // Clear fields
                Customer customer = null;

                Scenehandler.getInstance().textFieldMail.setText("");
                Scenehandler.getInstance().textFieldName.setText("");

                selectedCustomer = null;

                Scenehandler.getInstance().textFieldName.setDisable(false);
                Scenehandler.getInstance().textFieldMail.setDisable(false);

                Scenehandler.getInstance().buttonSaveAndExit.setDisable(true);

                // Updates the price
                priceLabel.setText("Price: " + Calculate.camperPrice(camper, selectedCustomer,
                        Integer.parseInt(comboBox.getValue().toString()),
                        weekStart)
                );
            }
        });
    }

    /**
     * Create the reservation dialog window
     * @param button that needs the functionality
     * @param camper that is selected
     * @param weekStart that is selected
     */
    public static void createNewReservation(Button button, Camper camper, int weekStart) {
        button.setOnAction(actionEvent -> {
            Scenehandler.getInstance().makeReservationStage(camper, weekStart);
            if (Controller.selectedCustomer==null){
                Scenehandler.getInstance().buttonSaveAndExit.setDisable(true);
            }
        });
    }

    /**
     * Exit the dialog window
     * @param button that needs the functionality
     */
    public static void exitDialog(Button button){
        button.setOnAction(event -> {
            Scenehandler.getInstance().getMainScene(selectedCustomer);
            Scenehandler.getInstance().dialog.close();
        });
    }

    /**
     * Calculate the price of the reservation
     * @param comboBox the box that needs this functionality
     * @param camper the specific camper
     * @param weekStart
     * @param priceLabel the label that will show the price
     */
    public static void calNewPrice(ComboBox comboBox, Camper camper, int weekStart, Label priceLabel) {
        comboBox.setOnAction(actionEvent -> {
            priceLabel.setText("Price: " + Calculate.camperPrice(camper, selectedCustomer,
                    Integer.parseInt(comboBox.getValue().toString()),
                    weekStart)
            );
        });
    }

    /**
     * Sets the functionality of the saveAndExit button, which makes a reservation
     * @param button that need the functionality
     * @param weekStart
     * @param comboBox the combobox with the week amount
     */
    public static void saveAndExitReservation(Button button,int weekStart, ComboBox comboBox) {

        // On button click, call this method
        button.setOnAction(actionEvent -> {

            // Make the reservation in the DB
            DB.makeDBReservation(weekStart,
                    Integer.parseInt(comboBox.getValue().toString()),
                    Scenehandler.getInstance().textFieldPhone.getText(),
                    Scenehandler.getInstance().textFieldLicensePlate.getText());

            // Close small window
            Scenehandler.getInstance().dialog.close();

            // Update the customers reservations
            selectedCustomer.updateReservations();

            // Update the main scene
            Scenehandler.getInstance().getMainScene(selectedCustomer);

            // Show it worked
            PopUp.popText("Reservation\nMade", "White", "25", App.stage);

        });

    }
}
