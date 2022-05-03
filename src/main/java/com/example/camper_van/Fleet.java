package com.example.camper_van;

import java.util.ArrayList;

public class Fleet {

    private static Fleet fleet=null;
    private ArrayList<Camper> campers;

    public static Fleet getInstance(){

        if (fleet==null){
            System.out.println("Fleet Created");
            fleet = new Fleet();
        } else {
            System.out.println("Fleet Exists");
        }

        return fleet;
    }

    public ArrayList<Camper> getCampers(){

        System.out.println("Get Campers");

        setCampers();

        return campers;
    }

    private void setCampers(){

        if (campers==null){
            campers = new ArrayList<>();
        } else if (campers.size()>0){
            campers.clear();
        }

        // Get the campers from the DB

        for (int i = 0; i <= 12; i++) {
            campers.add(new Camper("Plate"+i,"Brand"+i,"Model"+i,i%3+1,i,200,100));
        }

    }

}

class Camper {

    private String licensePlate;
    private String brand;
    private String model;
    private int priceCategory;
    private int seats;
    private int area;
    private int weight;

    private ArrayList<Integer> weeksOccupied;

    public Camper(String licensePlate,String brand, String model,int priceCategory, int seats, int area, int weight){
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.priceCategory = priceCategory;
        this.seats = seats;
        this.area = area;
        this.weight = weight;
    }

    public ArrayList<Integer> getWeeksOccupied(){

        setWeeksOccupied();

        return weeksOccupied;
    }

    private void setWeeksOccupied(){

        // Get the reservation information from the db via the licenseplate
        weeksOccupied = new ArrayList<>();


        ArrayList<Reservation> reservations = new ArrayList<>();


        if (this.getLicensePlate().equals("Plate1")){
            for (Reservation r:
                    CustomerList.getInstance().getCustomers().get(0).getReservations()) {
                reservations.add(r);
            }
        }


        for (Reservation r:
                reservations) {
            for (int i = r.getRentalStart(); i < r.getRentalStart()+r.getRentalWeeks(); i++) {
                weeksOccupied.add(i);
            }
        }

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

    public int getPriceCategory() {
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
}