package id.ac.binus.myapplication.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;

public class Car {
    private byte[] carImg;
    private String carId;
    private String brand;
    private String hostName;
    private String location;
    private String description;
    private int seats;
    private String transmission;
    private String model;
    private double pricePerDay;
    private String availability;
    private ArrayList<String> rules;
    private DatabaseHelper db;

    public Car(){

    }

    public Car(byte[] carImg, String carId, String brand, String hostName, String location, String description, int seats, String transmission, String model, double pricePerDay, String availability, ArrayList<String> rules) {
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

    public void updateCarStatus(Context context ,String carId){
        db = new DatabaseHelper(context);
        db.updateCarStatus(carId);
    }

    public String addCar(Context context, byte[] carImg, String carBrand, String carModel, String carHost,
                         int carSeats, String carTransmission, String carLocation,
                         double carPrice, String carDescription, String carRules){
        db = new DatabaseHelper(context);
        String carId = RandomIDGenerator.generateRandomID();
        Car car;

        ArrayList<String> rules = new ArrayList<>(Arrays.asList(carRules.split(",")));

        car = new Car(carImg, carId, carBrand, carHost, carLocation, carDescription, carSeats, carTransmission, carModel, carPrice, "Available", rules);

        long result = db.addCar(car);

        if(result != -1){
            return "Car added successfully!";
        } else{
            return "Failed to add car!";
        }
    }

    public Car getCarByCarId(Context context, String carId){
        db = new DatabaseHelper(context);
        return db.getCarByCarId(carId);
    }

    public void deleteCar(Context context, String carId){
        db = new DatabaseHelper(context);
        db.deleteCar(carId);
    }

    public String editCar(Context context, byte[] carImg, String carId, String carBrand, String carModel, String carHost,
                          int carSeats, String carTransmission, String carLocation,
                          double carPrice, String carDescription, String carRules){
        db = new DatabaseHelper(context);

        long result = db.editCar(carId, carImg, carBrand, carModel, carHost, carSeats, carTransmission, carLocation, carPrice, carDescription, carRules);

        if(result != -1){
            return "Car edited successfully!";
        } else{
            return "Failed to edit car!";
        }
    }

    public ArrayList<Car> getCarsByRole(Context context, String username){
        db = new DatabaseHelper(context);
        return db.getCarsByRole(username);
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


    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public String getAvailability() {
        return availability;
    }

    public byte[] getCarImg() {
        return carImg;
    }

    public String getHostName() {
        return hostName;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getSeats() {
        return seats;
    }

    public String getTransmission() {
        return transmission;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }
}
