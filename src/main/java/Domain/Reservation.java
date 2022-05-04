package Domain;

public class Reservation {

    private int rentalStart;
    private int rentalWeeks;
    private String customerPhone;
    private String vehicle;
    private int insurancePackage;

    public Reservation(int rentalStart, int rentalWeeks, String customerPhone, String vehicle, int insurancePackage){
        this.rentalStart = rentalStart;
        this.rentalWeeks = rentalWeeks;
        this.customerPhone = customerPhone;
        this.vehicle = vehicle;
        this.insurancePackage = insurancePackage;
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
