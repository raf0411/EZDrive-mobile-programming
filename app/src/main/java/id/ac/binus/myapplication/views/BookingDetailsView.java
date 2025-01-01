package id.ac.binus.myapplication.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class BookingDetailsView extends AppCompatActivity {

    ImageButton backBtn;
    ImageView carImgDetail;
    TextView carNameDetail, carHostDetail, carLocationDetail, carPriceDetail,
             carDescriptionDetail, carSeatsDetail, carTransmissionDetail, carRulesDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_details_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backBtn = findViewById(R.id.backBtn);
        String imagePath = getIntent().getStringExtra("carImg");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        String carName = getIntent().getStringExtra("carBrand") + " " + getIntent().getStringExtra("carModel");
        String carHost = "Hosted by " + getIntent().getStringExtra("carHost");
        String carLocation = getIntent().getStringExtra("carLocation");
        String carPrice = "Price: Rp." + getIntent().getDoubleExtra("carPricePerDay", 0) + " / day";
        String carDescription = getIntent().getStringExtra("carDescription");
        String carSeats = "Seats - " + getIntent().getIntExtra("carSeats", 0);
        String carTransmission = "Transmission - " + getIntent().getStringExtra("carTransmission");
        ArrayList<String> rules = getIntent().getStringArrayListExtra("carRules");
        assert rules != null;
        String carRules = "Rules: " + String.join(",", rules);

        carImgDetail = findViewById(R.id.carImgDetail);
        carNameDetail = findViewById(R.id.carNameDetail);
        carHostDetail = findViewById(R.id.carHostDetail);
        carLocationDetail = findViewById(R.id.carLocationDetail);
        carPriceDetail = findViewById(R.id.carPriceDetail);
        carDescriptionDetail = findViewById(R.id.carDescriptionDetail);
        carSeatsDetail = findViewById(R.id.carSeatsDetail);
        carTransmissionDetail = findViewById(R.id.carTransmissionDetail);
        carRulesDetail = findViewById(R.id.carRulesDetail);

        carImgDetail.setImageBitmap(bitmap);
        carNameDetail.setText(carName);
        carHostDetail.setText(carHost);
        carLocationDetail.setText(carLocation);
        carPriceDetail.setText(carPrice);
        carDescriptionDetail.setText(carDescription);
        carSeatsDetail.setText(carSeats);
        carTransmissionDetail.setText(carTransmission);
        carRulesDetail.setText(carRules);

        backBtn.setOnClickListener(view -> finish());
    }
}