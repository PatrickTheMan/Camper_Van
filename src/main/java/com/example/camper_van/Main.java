package com.example.camper_van;

public class Main{

    /*
    App -> choose PhoneNum -> see the history -> make new reservation
    choose week amount -> choose start week -> choose champer
     */

    public static void main(String[] args) {

        CustomerList.getInstance().getCustomers().get(0).getReservations().get(0);

        App.run();


    }
}
