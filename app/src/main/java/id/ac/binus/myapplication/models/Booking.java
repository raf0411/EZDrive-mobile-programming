package id.ac.binus.myapplication.models;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;

public class Booking {
    private String bookingId;
    private String userId;
    private String carid;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    DatabaseHelper db;

    public Booking(String bookingId, String userId, String carid, Date startDate, Date endDate, double totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.carid = carid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Booking(){

    }

    public String addBooking(Context context, Calendar startDate, Calendar endDate, String userId, String carId, double totalPrice){
        Date convertedStartDate = startDate.getTime();
        Date convertedEndDate = endDate.getTime();

        db = new DatabaseHelper(context);
        String bookingId = RandomIDGenerator.generateRandomID();
        Booking booking = new Booking(bookingId, userId, carId, convertedStartDate, convertedEndDate, totalPrice);
        long result = db.addBooking(booking);

        if(result != -1){
            return "Successful Booking!";
        } else{
            return "Booking Failed!";
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
