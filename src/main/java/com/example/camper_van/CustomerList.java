package com.example.camper_van;

import java.util.ArrayList;

public class CustomerList {

    private static CustomerList customerList;
    private static ArrayList<Customer> customers;

    public static CustomerList getInstance(){

        if (customerList==null){
            System.out.println("Create CustomerList");
            customerList = new CustomerList();
            setCustomers();
        } else {
            System.out.println("CustomerList exists");
        }

        return customerList;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    private static void setCustomers(){

        if (customers==null){
            customers = new ArrayList<>();
        } else if (customers.size()>0){
            System.out.println("CustomerList Cleared");
            customers.clear();
        }

        // Get the campers from the DB

        customers.add(new Customer("99999999","Patrick","Schemel","Buen 38","PatricksMail"));

        System.out.println("customers: "+customers);
    }

}

class Customer {

    private String phoneNum;
    private String firstName;
    private String lastName;
    private String address;
    private String eMail;

    private static ArrayList<Reservation> reservations;

    public Customer(String phoneNum,String firstName, String lastName, String address, String eMail){
        this.phoneNum = phoneNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.eMail = eMail;

        setReservations();
    }

    public ArrayList<Reservation> getReservations(){
        return reservations;
    }

    private static void setReservations(){

        System.out.println("Get Reservation");

        // Get the reservations via phonenumber from the db

        reservations = new ArrayList();

        for (int i = 0; i < 5; i++) {
            reservations.add(new Reservation(0,12,2,"99999999","TestVehicle",1));
        }
        reservations.add(new Reservation(0,11,1,"99999999","TestVehicle",1));

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