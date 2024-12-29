package id.ac.binus.myapplication.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;

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
    private String availability;
    private ArrayList<String> rules;
    private DatabaseHelper db;

    public Car(){

    }

    public Car(int carImg, String carId, String brand, String hostName, String location, String description, int seats, String transmission, String model, double pricePerDay, String availability, ArrayList<String> rules) {
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

    public String addCar(Context context, String carBrand, String carModel, String carHost,
                         int carSeats, String carTransmission, String carLocation,
                         double carPrice, String carDescription, String carRules){
        db = new DatabaseHelper(context);
        String carId = RandomIDGenerator.generateRandomID();
        Car car;

        ArrayList<String> rules = new ArrayList<>(Arrays.asList(carRules.split(",")));

        if(carBrand.equalsIgnoreCase("Toyota")){
            car = new Car(R.drawable.toyota_kijang_innova_zenix, carId, carBrand, carHost, carLocation, carDescription, carSeats, carTransmission, carModel, carPrice, "Available", rules);
        } else if(carBrand.equalsIgnoreCase("Mitsubishi")){
            car = new Car(R.drawable.mitsubishi_xpander, carId, carBrand, carHost, carLocation, carDescription, carSeats, carTransmission, carModel, carPrice, "Available", rules);
        } else if(carBrand.equalsIgnoreCase("Honda")){
            car = new Car(R.drawable.honda_brv, carId, carBrand, carHost, carLocation, carDescription, carSeats, carTransmission, carModel, carPrice, "Available", rules);
        } else {
            car = new Car(R.drawable.tesla, carId, carBrand, carHost, carLocation, carDescription, carSeats, carTransmission, carModel, carPrice, "Available", rules);
        }

        long result = db.addCar(car);

        if(result != -1){
            return "Car added successfully!";
        } else{
            return "Failed to add car!";
        }
    }

    public void deleteCar(Context context, String carId){
        db = new DatabaseHelper(context);
        db.deleteCar(carId);
    }

    public String editCar(Context context, String carId, String carBrand, String carModel, String carHost,
                          int carSeats, String carTransmission, String carLocation,
                          double carPrice, String carDescription, String carRules){
        db = new DatabaseHelper(context);

        long result = db.editCar(carId, carBrand, carModel, carHost, carSeats, carTransmission, carLocation, carPrice, carDescription, carRules);

        if(result != -1){
            return "Car edited successfully!";
        } else{
            return "Failed to edit car!";
        }
    }

    public ArrayList<Car> getAllCars(Context context){
        db = new DatabaseHelper(context);
        return db.getAllCars();
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
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
