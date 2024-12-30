package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.adapters.BookingAdapter;
import id.ac.binus.myapplication.controllers.BookingController;
import id.ac.binus.myapplication.models.Booking;

public class BookingHistoryView extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView bookingHistoryList;
    TextView noBookingsTV;
    BookingController bookingController = new BookingController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_history_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);
        String userId = prefs.getString("userId", "NONE");
        Log.d("BookingHistoryView", "userId: " + userId);
        bookingHistoryList = findViewById(R.id.bookingHistoryList);
        noBookingsTV = findViewById(R.id.noBookingsTV);
        backBtn = findViewById(R.id.backBtn);

        ArrayList<Booking> bookings = bookingController.getAllBookings(this, userId);

        if(bookings.isEmpty() || bookings == null){
            noBookingsTV.setVisibility(View.VISIBLE);
            bookingHistoryList.setVisibility(View.GONE);
        } else{
            bookingHistoryList.setVisibility(View.VISIBLE);
            noBookingsTV.setVisibility(View.GONE);
            BookingAdapter bookingAdapter = new BookingAdapter(bookings, this);
            bookingHistoryList.setLayoutManager(new LinearLayoutManager(this));
            bookingHistoryList.setAdapter(bookingAdapter);
        }

        backBtn.setOnClickListener(view -> finish());
    }
}