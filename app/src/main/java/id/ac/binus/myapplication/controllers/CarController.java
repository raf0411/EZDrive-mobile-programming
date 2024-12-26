package id.ac.binus.myapplication.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.binus.myapplication.models.Car;

public class CarController {
    Car car = new Car();

    public CarController(){

    }

    public String addCar(Context context, String carBrand, String carModel, String carHost,
                         int carSeats, String carTransmission, String carLocation,
                         double carPrice, String carDescription, ArrayList<String> carRules){

        if(carBrand.isEmpty()){
            return "Car Brand is empty!";
        } else if(carModel.isEmpty()){
            return "Car Model is empty!";
        } else if(carHost.isEmpty()){
            return "Car host is empty!";
        } else if (carSeats <= 0){
            return "Car seats must be at least 1!";
        } else if(carTransmission.isEmpty()){
            return "Car transmission is empty!";
        } else if(!(carTransmission.equals("Manual") || carTransmission.equals("Automatic"))){
            return "Car transmission must be either 'Automatic' or 'Manual'!";
        } else if(carLocation.isEmpty()){
            return "Car location is empty!";
        } else if(carPrice == 0.00){
            return "Car price must be more than 0.00!";
        } else if(carDescription.isEmpty()){
            return "Car description is empty!";
        } else if(carRules.isEmpty() || carRules == null){
            return "Car rules is empty!";
        } else {
            return car.addCar(context, carBrand, carModel, carHost, carSeats, carTransmission, carLocation, carPrice, carDescription, carRules);
        }
    }
}
