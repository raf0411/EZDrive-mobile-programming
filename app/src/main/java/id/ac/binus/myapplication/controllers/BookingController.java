package id.ac.binus.myapplication.controllers;

import android.content.Context;

import java.util.Calendar;

import id.ac.binus.myapplication.models.Booking;

public class BookingController {
    Booking booking = new Booking();

    public BookingController() {

    }

    public String validateCarBooking(Context context, Calendar startDate, Calendar endDate, String userId,
                                     String carId, double totalPrice){
        Calendar currentDate = Calendar.getInstance();

        if (endDate.before(startDate)) {
            return "End Date must be after Start Date!";
        } else if(startDate.before(currentDate) || endDate.before(currentDate)){
            return "Start Date or End Date must be before your Current Date!";
        } else {
            return booking.addBooking(context, startDate, endDate, userId, carId, totalPrice);
        }
    }
}
