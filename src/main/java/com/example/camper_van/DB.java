package com.example.camper_van;

import java.sql.*;
import java.util.ArrayList;


public class DB {

    /**
     *  make a reservation
     * @param licencePlate the chosen camper
     * @param weekStart the start week
     */
    public static void makeReservation(String licencePlate, int weekStart){

    }

    /**
     *  get a specific camper
     * @return the license plate num
     */
    public static String getCamper(){

        return "";
    }

    public static ArrayList getCampers(){

        ArrayList campers = new ArrayList();

        return campers;
    }

    /**
     *  get the availableWeeks
     * @param weekAmount the amount of week you want to rent
     * @return a list of all the available start weeks
     */
    public static ArrayList getAvailableWeeks(int weekAmount){

        ArrayList availableWeeks = new ArrayList();



        return availableWeeks;
    }

}

class connection {

    private static Connection con;

    /**
     * get the instance connection
     * @return the connection
     */
    public static Connection getInstance()
    {
        if (con == null){
            connect();
        }

        return con;
    }

    /**
     *  Close the instance connection
     */
    public static void closeInstance(){
        disconnect();
    }

    /**
     *  establishes the connection to the database of the Animal Shelter
     */
    private static void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DB_Bike","Patrick","123456");
            //System.out.println("Connected to "+con.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * disconnects from the database
     */
    private static void disconnect() {
        try {
            //System.out.println("Disconnected from "+con.getCatalog());
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}