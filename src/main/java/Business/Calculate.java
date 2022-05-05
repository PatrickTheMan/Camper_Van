package Business;

import Domain.Camper;
import Domain.Customer;
import Domain.Season;

import java.util.ArrayList;

public class Calculate {

    /**
     * Calculates the price based on the camper, customer, weekAmount and weekStart (also lookes at season and customer discount)
     * @param camper the reservated camper
     * @param customer the customer that wants to reservate
     * @param weekAmount the weekAmount wished to reservate
     * @param weekStart the wekkStart wished to reservate
     * @return the price as a double
     */
    public static double camperPrice(Camper camper, Customer customer, int weekAmount, int weekStart) {

        double price = 0;

        // Add the weeks you want to rent to an array
        ArrayList<Integer> seasonWeeks = Season.getInstance().getSeason();

        // Times the week amount
        for (int i = weekStart; i < weekStart + weekAmount; i++) {
            if (seasonWeeks.contains(i)) {
                // Camper price pr week season
                price+=camper.getPrice()*1.2;
            } else {
                // Camper price pr week non-season
                price+=camper.getPrice();
            }
        }

        // Get the discount if you have the customer
        if (customer != null) {
            if (customer.getReservations().size() > 5) {// Get the amount of tripes
                price = price * 0.9;
            } else if (customer.getReservations().size() > 2) {
                price = price * 0.95;
            }
        }

        return price;
    }

}
