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

    /**
     *  get all the campers
     * @return
     */
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

class DBConnection {

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
     *  establishes the connection to the database of the Animal Shelter
     */
    private static void connect() {
        try {
            System.out.println("Connecting "+con.getCatalog());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DB_Bike","Patrick","123456");
            System.out.println("Connected to "+con.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}