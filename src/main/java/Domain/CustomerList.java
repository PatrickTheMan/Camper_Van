package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class CustomerList {

    private static CustomerList customerList;
    private static ArrayList<Customer> customers;

    /**
     * Gets the CustomerList singleton
     * @return the instance
     */
    public static CustomerList getInstance(){

        if (customerList==null){
            customerList = new CustomerList();
            updateCustomers();
        }

        return customerList;
    }

    /**
     * Gets the customers
     * @return the customers
     */
    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    /**
     * Gets a single customer
     * @param phoneNum of the customer that gets returned
     * @return the specific customer
     */
    public Customer getCustomer (String phoneNum){
        for (Customer c:
             customers) {
            if (c.getPhoneNum().equals(phoneNum)){
                return c;
            }
        }
        return null;
    }

    /**
     * Set the customers via DB
     */
    private static void updateCustomers(){

        if (customers==null){
            customers = new ArrayList<>();
        }

        // Get the campers from the DB
        customers = DB.getCustomers();
    }

}

