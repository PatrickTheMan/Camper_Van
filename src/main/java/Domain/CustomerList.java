package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class CustomerList {

    private static CustomerList customerList;
    private static ArrayList<Customer> customers;

    public static CustomerList getInstance(){

        if (customerList==null){
            customerList = new CustomerList();
            setCustomers();
        }

        return customerList;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public Customer getCustomer (String phoneNum){
        for (Customer c:
             customers) {
            if (c.getPhoneNum().equals(phoneNum)){
                return c;
            }
        }
        return null;
    }

    private static void setCustomers(){

        if (customers==null){
            customers = new ArrayList<>();
        }

        // Get the campers from the DB
        customers = DB.getCustomers();
    }

}

