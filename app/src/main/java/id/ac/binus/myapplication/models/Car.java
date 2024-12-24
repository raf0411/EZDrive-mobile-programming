package id.ac.binus.myapplication.models;

public class Car {
    private int carImg;
    private String carId;
    private String brand;
    private String model;
    private double pricePerDay;
    private Boolean availability;

    public Car(){

    }

    public Car(int carImg, String carId, String brand, String model, double pricePerDay, Boolean availability) {
        this.carImg = carImg;
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public int getCarImg() {
        return carImg;
    }

    public void setCarImg(int carImg) {
        this.carImg = carImg;
    }
}
