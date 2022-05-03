package com.example.camper_van;

public class Reservation {

    private int id;
    private int rentalStart;
    private int rentalWeeks;
    private String customerPhone;
    private String vehicle;
    private int insurancePackage;

    public Reservation(int id, int rentalStart, int rentalWeeks, String customerPhone, String vehicle, int insurancePackage){
        this.id = id;
        this.rentalStart = rentalStart;
        this.rentalWeeks = rentalWeeks;
        this.customerPhone = customerPhone;
        this.vehicle = vehicle;
        this.insurancePackage = insurancePackage;
    }

    public int getId() {
        return id;
    }

    public int getRentalStart() {
        return rentalStart;
    }

    public int getRentalWeeks() {
        return rentalWeeks;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public int getInsurancePackage() {
        return insurancePackage;
    }
}
