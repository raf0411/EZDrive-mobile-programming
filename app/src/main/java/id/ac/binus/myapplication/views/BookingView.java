package id.ac.binus.myapplication.views;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import id.ac.binus.myapplication.MainActivity;
import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.BookingController;

public class BookingView extends AppCompatActivity {

    private final static double TAX = 160000.0;
    BookingController bookingController = new BookingController();
    double totalRentalPrice;

    TextView carNameBooking, carHostBooking, carLocationBooking, carPriceBooking,
             totalDaysData, subTotalPriceData, taxData, totalRentalPriceData;
    Button processPaymentBtn;
    ImageButton backBtn;
    DatePicker startDateInput, endDateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        double price = getIntent().getDoubleExtra("carPrice", 0.00);
        String stringPrice = "Price : " + price + " / day";

        processPaymentBtn = findViewById(R.id.processPaymentBtn);
        backBtn = findViewById(R.id.backBtn);
        carNameBooking = findViewById(R.id.carNameBooking);
        carHostBooking = findViewById(R.id.carHostBooking);
        carLocationBooking = findViewById(R.id.carLocationBooking);
        carPriceBooking = findViewById(R.id.carPriceBooking);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        totalDaysData = findViewById(R.id.totalDaysData);
        subTotalPriceData = findViewById(R.id.subTotalPriceData);
        taxData = findViewById(R.id.taxData);
        totalRentalPriceData = findViewById(R.id.totalRentalPrice);

        startDateInput.init(startDateInput.getYear(), startDateInput.getMonth(), startDateInput.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> updatePaymentDetails());

        endDateInput.init(endDateInput.getYear(), endDateInput.getMonth(), endDateInput.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> updatePaymentDetails());

        carNameBooking.setText(getIntent().getStringExtra("carName"));
        carHostBooking.setText(getIntent().getStringExtra("carHost"));
        carPriceBooking.setText(stringPrice);
        carLocationBooking.setText(getIntent().getStringExtra("carLocation"));

        backBtn.setOnClickListener(view -> finish());

        if (isDatePicked(startDateInput) && isDatePicked(endDateInput)) {
            updatePaymentDetails();
        }

        processPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);
                String carId = getIntent().getStringExtra("carId");
                String userId = prefs.getString("userId", "NONE");
                Calendar startDate = Calendar.getInstance();
                startDate.set(startDateInput.getYear(), startDateInput.getMonth(), startDateInput.getDayOfMonth());
                Calendar endDate = Calendar.getInstance();
                endDate.set(endDateInput.getYear(), endDateInput.getMonth(), endDateInput.getDayOfMonth());

                String message = bookingController.validateCarBooking(BookingView.this, startDate, endDate, userId, carId, totalRentalPrice);
                Toast.makeText(BookingView.this, message, Toast.LENGTH_SHORT).show();

                if(message.equals("Successful Booking!")){
                    Intent intent = new Intent(BookingView.this, CarListingsView.class);
                    intent.putExtra("username", prefs.getString("username", "NONE"));
                    sendNotification();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isDatePicked(DatePicker datePicker) {
        Calendar today = Calendar.getInstance();
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        return selectedDate.after(today) || selectedDate.equals(today);
    }

    @SuppressLint("SetTextI18n")
    private void updatePaymentDetails() {
        double price = getIntent().getDoubleExtra("carPrice", 0.00);
        long totalDays = calculateDays();

        if (totalDays == 0){
            totalDays = 1;
        }

        double subTotal = price * totalDays;
        totalRentalPrice = subTotal + TAX;

        totalDaysData.setText("Total days: " + totalDays);
        subTotalPriceData.setText("Subtotal: Rp." + subTotal);
        taxData.setText("Tax: Rp." + TAX);
        totalRentalPriceData.setText("Total Rental Price: Rp." + totalRentalPrice);
    }

    private void sendNotification() {
        String title = "Booking Confirmed";
        String body = "Your car booking has been successfully processed!";
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_high_priority_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                channelId,
                "High Priority Notifications",
                NotificationManager.IMPORTANCE_HIGH
        );

        channel.setDescription("This channel is used for high-priority notifications.");
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public long calculateDays(){
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDateInput.getYear(), startDateInput.getMonth(), startDateInput.getDayOfMonth());

        Calendar endDate = Calendar.getInstance();
        endDate.set(endDateInput.getYear(), endDateInput.getMonth(), endDateInput.getDayOfMonth());

        long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(diffInMillis);
    }
}