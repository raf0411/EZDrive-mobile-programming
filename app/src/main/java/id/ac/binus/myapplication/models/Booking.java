package id.ac.binus.myapplication.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;

public class Booking {
    private String bookingId;
    private String userId;
    private String carId;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    private String carName;
    private double carPricePerDay;

    DatabaseHelper db;
    Car car = new Car();

    public Booking(String bookingId, String userId, String carId, Date startDate, Date endDate, double totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Booking(String carId, String carName, double carPricePerDay, Date startDate, Date endDate, double totalPrice){
        this.carId = carId;
        this.carName = carName;
        this.carPricePerDay = carPricePerDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Booking(){

    }

    public ArrayList<Booking> getAllBookings(Context context, String userId){
        db = new DatabaseHelper(context);
        return db.getAllBookings(userId);
    }

    public String addBooking(Context context, Calendar startDate, Calendar endDate, String userId, String carId, double totalPrice){
        Date convertedStartDate = startDate.getTime();
        Date convertedEndDate = endDate.getTime();

        db = new DatabaseHelper(context);
        String bookingId = RandomIDGenerator.generateRandomID();
        Booking booking = new Booking(bookingId, userId, carId, convertedStartDate, convertedEndDate, totalPrice);
        long result = db.addBooking(booking);

        if(result != -1){
            car.updateCarStatus(context, carId);
            return "Successful Booking!";
        } else{
            return "Booking Failed!";
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCarName() {
        return carName;
    }

    public double getCarPricePerDay() {
        return carPricePerDay;
    }
}
