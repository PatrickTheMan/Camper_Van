package Application;

import Business.Calculate;
import Domain.Camper;
import Domain.Customer;
import Foundation.DB;
import UI.App;
import UI.PopUp;
import UI.Scenehandler;
import Domain.CustomerList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {

    public static Customer selectedCustomer;

    public static void searchPhoneNumber(ArrayList<Customer> customers, TextField textField) {

        textField.setOnKeyTyped(actionEvent -> {

            if (textField.getText().length() == 8) {

                Customer customer = null;

                // Search for the customer
                for (Customer c :
                        customers) {

                    // Has been found
                    if (c.getPhoneNum().equals(textField.getText())) {
                        customer = c;
                        break;
                    }
                }

                if (customer == null) {
                    textField.setText("");
                    PopUp.popText("User Not Found", "#FF6961", "25", App.stage);
                } else {
                    PopUp.popText("User Found", "White", "25", App.stage);
                    selectedCustomer = customer;
                    Scenehandler.getInstance().getMainScene(customer);
                }
            }
        });
    }

    public static void searchPhoneNumber(ArrayList<Customer> customers, TextField textField, Camper camper, String weekStart) {

        textField.setOnKeyTyped(actionEvent -> {

            if (textField.getText().length() == 8) {

                Customer customer = null;

                // Search for the customer
                for (Customer c :
                        customers) {

                    // Has been found
                    if (c.getPhoneNum().equals(textField.getText())) {
                        customer = c;
                        break;
                    }
                }

                if (customer == null) {
                    PopUp.popText("User Not Found", "#FF6961", "25", Scenehandler.getInstance().dialog);

                } else {

                    PopUp.popText("User Found", "White", "25", Scenehandler.getInstance().dialog);

                    selectedCustomer = customer;

                    Scenehandler.getInstance().textFieldMail.setText(customer.geteMail());
                    Scenehandler.getInstance().textFieldName.setText(customer.getFirstName() + " " + customer.getLastName());

                    Scenehandler.getInstance().textFieldName.setDisable(true);
                    Scenehandler.getInstance().textFieldMail.setDisable(true);

                    Scenehandler.getInstance().buttonSaveAndExit.setDisable(false);

                }
            } else if (textField.getText().length() != 8 && !Scenehandler.getInstance().textFieldName.getText().equals("")) {

                Customer customer = null;

                // CLEAR
                Scenehandler.getInstance().textFieldMail.setText("");
                Scenehandler.getInstance().textFieldName.setText("");

                selectedCustomer = null;

                Scenehandler.getInstance().textFieldName.setDisable(false);
                Scenehandler.getInstance().textFieldMail.setDisable(false);

                Scenehandler.getInstance().buttonSaveAndExit.setDisable(true);

            }

        });
    }

    public static void createNewReservation(Button button, Customer customer, Camper camper, String weekStart) {
        button.setOnAction(actionEvent -> {
            Scenehandler.getInstance().makeReservationStage(customer, camper, weekStart);
            if (Controller.selectedCustomer==null){
                Scenehandler.getInstance().buttonSaveAndExit.setDisable(true);
            }
        });
    }

    public static void exitDialog(Button button, Stage stage){
        button.setOnAction(event -> {

            Scenehandler.getInstance().getMainScene(selectedCustomer);

            stage.close();
        });
    }

    public static void calNewPrice(ComboBox comboBox, Camper camper, Customer customer, int weekStart, Label priceLabel) {

        comboBox.setOnAction(actionEvent -> {
            priceLabel.setText("Price: " + Calculate.camperPrice(camper, customer,
                    Integer.parseInt(comboBox.getValue().toString()),
                    weekStart));
        });
    }

    public static void saveAndExitReservation(Button button,int weekStart, ComboBox comboBox, Stage stage) {

        // On button click, call this method
        button.setOnAction(actionEvent -> {

            // Make the reservation in the DB
            DB.makeDBReservation(weekStart,
                    Integer.parseInt(comboBox.getValue().toString()),
                    Scenehandler.getInstance().textFieldPhone.getText(),
                    Scenehandler.getInstance().textFieldLicensePlate.getText());

            // Close small window
            stage.close();

            // Update the customers reservations
            selectedCustomer.updateReservations();

            // Update the main scene
            Scenehandler.getInstance().getMainScene(selectedCustomer);

            // Show it worked
            PopUp.popText("Reservation\nMade", "White", "25", Scenehandler.getInstance().dialog);

        });

    }
}
