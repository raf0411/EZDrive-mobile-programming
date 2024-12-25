package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import id.ac.binus.myapplication.R;

public class CarDetailsView extends AppCompatActivity {

    ImageButton backBtn;
    Button bookNowBtn;
    ImageView carImgDetail;
    TextView carNameDetail, carHostDetail, carLocationDetail, carPriceDetail,
             carDescriptionDetail, carSeatsDetail, carTransmissionDetail, carRulesDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_details_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookNowBtn = findViewById(R.id.bookNowBtn);
        backBtn = findViewById(R.id.backBtn);

        int carImg = getIntent().getIntExtra("carImg", 0);
        String carName = getIntent().getStringExtra("carBrand") + " " + getIntent().getStringExtra("carModel");
        String carHost = "Hosted by " + getIntent().getStringExtra("carHost");
        String carLocation = getIntent().getStringExtra("carLocation");
        double carActualPrice = getIntent().getDoubleExtra("carPricePerDay", 0.00);
        String carPrice = "Price: Rp." + getIntent().getDoubleExtra("carPricePerDay", 0.00) + " / day";
        String carDescription = getIntent().getStringExtra("carDescription");
        String carSeats = "Seats - " + getIntent().getIntExtra("carSeats", 0);
        String carTransmission = "Transmission - " + getIntent().getStringExtra("carTransmission");
        ArrayList<String> rules = getIntent().getStringArrayListExtra("carRules");
        assert rules != null;
        String carRules = "Rules: " + String.join(", ", rules);

        carImgDetail = findViewById(R.id.carImgDetail);
        carNameDetail = findViewById(R.id.carNameDetail);
        carHostDetail = findViewById(R.id.carHostDetail);
        carLocationDetail = findViewById(R.id.carLocationDetail);
        carPriceDetail = findViewById(R.id.carPriceDetail);
        carDescriptionDetail = findViewById(R.id.carDescriptionDetail);
        carSeatsDetail = findViewById(R.id.carSeatsDetail);
        carTransmissionDetail = findViewById(R.id.carTransmissionDetail);
        carRulesDetail = findViewById(R.id.carRulesDetail);

        carImgDetail.setImageResource(carImg);
        carNameDetail.setText(carName);
        carHostDetail.setText(carHost);
        carLocationDetail.setText(carLocation);
        carPriceDetail.setText(carPrice);
        carDescriptionDetail.setText(carDescription);
        carSeatsDetail.setText(carSeats);
        carTransmissionDetail.setText(carTransmission);
        carRulesDetail.setText(carRules);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CarDetailsView.this, CarListingsView.class);
            startActivity(intent);
        });

        bookNowBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CarDetailsView.this, BookingView.class);
            intent.putExtra("carName", carName);
            intent.putExtra("carHost", carHost);
            intent.putExtra("carLocation", carLocation);
            intent.putExtra("carPrice", carActualPrice);

            startActivity(intent);
        });
    }
}