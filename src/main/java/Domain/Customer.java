package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class Customer {

    private String phoneNum;
    private String firstName;
    private String lastName;
    private String address;
    private String eMail;

    private ArrayList<Reservation> reservations;

    public Customer(String phoneNum, String firstName, String lastName, String address, String eMail) {
        this.phoneNum = phoneNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.eMail = eMail;

        updateReservations();
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void updateReservations() {

        // Get the reservations via phonenumber from the db
        reservations = DB.getReservationPhone(this.phoneNum);

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String geteMail() {
        return eMail;
    }
}
