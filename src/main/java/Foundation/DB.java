package Foundation;

import Domain.Camper;
import Domain.Customer;
import Domain.Reservation;

import java.sql.*;
import java.util.ArrayList;


public class DB {

    /**
     *  Make a connection if there isn't any
     */
    public DB(){
        if (!DBConnection.isConnected()){
            DBConnection.getInstance();
        }
    }

    /**
     * Makes a reservation in the DB
     * @param weekStart
     * @param weekAmount
     * @param phoneNum
     * @param licensePlate
     */
    public static void makeDBReservation(int weekStart, int weekAmount, String phoneNum, String licensePlate){

        try {
            // Makes prepared statement with the given reservation details and stored procedure
            PreparedStatement ps = DBConnection.getInstance().prepareStatement(
                    "exec [dbo].[makeReservation] "+weekStart+","+weekAmount+",'"+phoneNum+"','"+licensePlate+"',0");
            // Executes the stored procedure
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     *  get all the campers
     * @return
     */
    public static ArrayList<Camper> getCampers(){

        ArrayList<Camper> campers = new ArrayList();

        try {
            // Makes a prepared statement with the stored procedure
            PreparedStatement ps = DBConnection.getInstance().prepareStatement("exec [dbo].[getCamper]");
            // Executes the stored procedure until no data has been found
            ResultSet rs = ps.executeQuery();

            // Add a camper at a time
            while (rs.next()){

                // Adding camper
                campers.add(new Camper(
                        cutString(rs.getString(1)),
                        cutString(rs.getString(2)),
                        cutString(rs.getString(3)),
                        cutString(rs.getString(4)),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8)
                ));

            }

            return campers;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * get all customers
     * @return the customers in an arraylist
     */
    public static ArrayList<Customer> getCustomers(){

        ArrayList<Customer> customers = new ArrayList();

        try {
            // Makes a prepared statement with the stored procedure
            PreparedStatement ps = DBConnection.getInstance().prepareStatement("exec [dbo].[getCustomer]");
            // Executes the stored procedure until no data has been found
            ResultSet rs = ps.executeQuery();

            // Add each customer
            while (rs.next()){

                // Add customer
                customers.add(new Customer(
                        cutString(rs.getString(1)),
                        cutString(rs.getString(2)),
                        cutString(rs.getString(3)),
                        cutString(rs.getString(4)),
                        cutString(rs.getString(5))
                ));

            }

            return customers;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get the reservated weeks for a specific camper
     * @param licensePlate the campers
     * @return a arraylist with the occupied weeks
     */
    public static ArrayList<Integer> getReservationLicense(String licensePlate){

        ArrayList<Integer> occupiedWeeks = new ArrayList();

        try {
            // Makes a prepared statement with the information and the stored procedure
            PreparedStatement ps = DBConnection.getInstance().prepareStatement("exec [dbo].[getReservationsLicense] '"+licensePlate+"'");
            // Executes the stored procedure until no data has been found
            ResultSet rs = ps.executeQuery();

            // Adds the reservations
            while (rs.next()){

                // Gets the start week and week amount
                int weekStart = rs.getInt(2);
                int weekAmount = rs.getInt(3);

                // Add the weeks one by one
                for (int i = weekStart; i < weekStart+weekAmount; i++) {
                    occupiedWeeks.add(i);
                }

            }

            return occupiedWeeks;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get the reservations for a specific camper
     * @param phoneNum the customers
     * @return a arraylist with the reservations
     */
    public static ArrayList<Reservation> getReservationPhone(String phoneNum){

        ArrayList<Reservation> reservations = new ArrayList();

        try {
            // Makes a prepared statement with the information and the stored procedure
            PreparedStatement ps = DBConnection.getInstance().prepareStatement("exec [dbo].[getReservationsPhone] '"+phoneNum+"'");
            // Executes the stored procedure until no data has been found
            ResultSet rs = ps.executeQuery();

            // Adds the reservations
            while (rs.next()){

                // Add reservation
                reservations.add(new Reservation(
                    rs.getInt(2),
                    rs.getInt(3),
                    cutString(rs.getString(4)),
                    cutString(rs.getString(5)),
                    0
                ));

            }

            return reservations;

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Cuts the string at the end
     * @param s the string to be cut down in size
     * @return the formatted string
     */
    private static String cutString(String s){

        char c;

        // Cut the string when there is a SPACE and return it
        for (int i = 0; i < s.length(); i++) {

            c = s.charAt(i);

            if (c == ' '){
                s = s.substring(0,i);
            }
        }

        return s;
    }



}

