package id.ac.binus.myapplication.models;

import java.util.ArrayList;

public class Car {
    private int carImg;
    private String carId;
    private String brand;
    private String hostName;
    private String location;
    private String description;
    private int seats;
    private String transmission;
    private String model;
    private double pricePerDay;
    private Boolean availability;
    private ArrayList<String> rules;

    public Car(){

    }

    public Car(int carImg, String carId, String brand, String hostName, String location, String description, int seats, String transmission, String model, double pricePerDay, Boolean availability, ArrayList<String> rules) {
        this.carImg = carImg;
        this.carId = carId;
        this.brand = brand;
        this.hostName = hostName;
        this.location = location;
        this.description = description;
        this.seats = seats;
        this.transmission = transmission;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
        this.rules = rules;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }
}
