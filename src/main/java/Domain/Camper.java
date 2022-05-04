package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class Camper {

    private String licensePlate;
    private String brand;
    private String model;
    private String priceCategory;
    private int seats;
    private int area;
    private int weight;
    private int price;

    private ArrayList<Integer> weeksOccupied;

    public Camper(String licensePlate, String brand, String model, String priceCategory, int seats, int area, int weight, int price) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.priceCategory = priceCategory;
        this.seats = seats;
        this.area = area;
        this.weight = weight;
        this.price = price;
    }

    public ArrayList<Integer> getWeeksOccupied() {

        setWeeksOccupied();

        return weeksOccupied;
    }

    private void setWeeksOccupied() {

        // Get the reservation information from the db via the licenseplate
        weeksOccupied = DB.getReservationLicense(this.licensePlate);

    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getPriceCategory() {
        return priceCategory;
    }

    public int getSeats() {
        return seats;
    }

    public int getArea() {
        return area;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }
}
