package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.BookingController;

public class BookingView extends AppCompatActivity {

    private final static double TAX = 160000.00; // Rp. 160.000
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

        carNameBooking.setText(getIntent().getStringExtra("carName"));
        carHostBooking.setText(getIntent().getStringExtra("carHost"));
        carPriceBooking.setText(stringPrice);
        carLocationBooking.setText(getIntent().getStringExtra("carLocation"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingView.this, CarDetailsView.class);
                startActivity(intent);
            }
        });

        processPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!totalRentalPriceData.getText().equals("Total Rental Price : Rp. x.xxx.xxx")){
                    SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);
                    String carId = getIntent().getStringExtra("carId");
                    String userId = prefs.getString("userId", "NONE");
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(startDateInput.getYear(), startDateInput.getMonth(), startDateInput.getDayOfMonth());
                    Calendar endDate = Calendar.getInstance();
                    endDate.set(endDateInput.getYear(), endDateInput.getMonth(), endDateInput.getDayOfMonth());

                    Intent intent = new Intent(BookingView.this, CarListingsView.class);
                    String message = bookingController.validateCarBooking(BookingView.this, startDate, endDate, userId, carId, totalRentalPrice);
                    Toast.makeText(BookingView.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

                long totalDays = calculateDays();
                if (totalDays == 0){
                    totalDays = 1;
                }
                double subTotal = price * Double.parseDouble(String.valueOf(totalDays));
                totalRentalPrice = subTotal + TAX;

                totalDaysData.setText("Total days: " + totalDays);
                subTotalPriceData.setText("Subtotal: Rp." + subTotal);
                taxData.setText("Tax: Rp." + TAX);
                totalRentalPriceData.setText("Total Rental Price: Rp." + totalRentalPrice);
            }
        });
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