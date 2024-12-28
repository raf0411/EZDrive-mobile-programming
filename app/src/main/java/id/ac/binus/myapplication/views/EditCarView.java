package id.ac.binus.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.CarController;

public class EditCarView extends AppCompatActivity {

    ImageButton editCarBackBtn;
    EditText carBrandEditText, carModelEditText, carHostEditText, carSeatsEditText,
             carTransmissionEditText, carLocationEditText, carPriceEditText, carDescriptionEditText, carRulesEditText;
    Button editCarBtn;
    TextView editCarErrorLbl;
    CarController carController = new CarController();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_view);

        editCarBackBtn = findViewById(R.id.editCarBackbtn);
        carBrandEditText = findViewById(R.id.carBrandEditText);
        carModelEditText = findViewById(R.id.carModelEditText);
        carHostEditText = findViewById(R.id.carHostEditText);
        carSeatsEditText = findViewById(R.id.carSeatsEditText);
        carTransmissionEditText = findViewById(R.id.carTransmissionEditText);
        carLocationEditText = findViewById(R.id.carLocationEditText);
        carPriceEditText = findViewById(R.id.carPriceEditText);
        carDescriptionEditText = findViewById(R.id.carDescriptionEditText);
        carRulesEditText = findViewById(R.id.carRulesEditText);
        editCarBtn = findViewById(R.id.editCarBtn);
        editCarErrorLbl = findViewById(R.id.editCarErrorLbl);

        String carId = getIntent().getStringExtra("carId");
        String currentBrand = getIntent().getStringExtra("carBrand");
        String currentModel = getIntent().getStringExtra("carModel");
        String currentHost = getIntent().getStringExtra("carHost");
        int currentSeats = getIntent().getIntExtra("carSeats", 0);
        String currentTransmission = getIntent().getStringExtra("carTransmission");
        String currentLocation = getIntent().getStringExtra("carLocation");
        double currentPrice = getIntent().getDoubleExtra("carPrice", 0.00);
        String currentDesc = getIntent().getStringExtra("carDescription");
        ArrayList<String> currentRules = getIntent().getStringArrayListExtra("carRules");

        String convertedRules = (currentRules != null) ? String.join(",", currentRules) : "";

        carBrandEditText.setText(currentBrand);
        carModelEditText.setText(currentModel);
        carHostEditText.setText(currentHost);
        carSeatsEditText.setText(String.valueOf(currentSeats));
        carTransmissionEditText.setText(currentTransmission);
        carLocationEditText.setText(currentLocation);
        carPriceEditText.setText(String.valueOf(currentPrice));
        carDescriptionEditText.setText(currentDesc);
        carRulesEditText.setText(convertedRules);

        editCarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCarView.this, CarListingsView.class);
                startActivity(intent);
            }
        });

        editCarBtn.setOnClickListener(view -> {
            String newCarBrand = carBrandEditText.getText().toString();
            String newCarModel = carModelEditText.getText().toString();
            String newCarHost = carHostEditText.getText().toString();
            int newCarSeats = Integer.parseInt(carSeatsEditText.getText().toString());
            String newCarTransmission = carTransmissionEditText.getText().toString();
            String newCarLocation = carLocationEditText.getText().toString();
            double newCarPrice = Double.parseDouble(carPriceEditText.getText().toString());
            String newCarDescription = carDescriptionEditText.getText().toString();
            String newCarRules = carRulesEditText.getText().toString();

            String message = carController.editCar(EditCarView.this, carId, newCarBrand, newCarModel, newCarHost, newCarSeats, newCarTransmission, newCarLocation, newCarPrice, newCarDescription, newCarRules);
            editCarErrorLbl.setText(message);

            if (message.equals("Car edited successfully!")) {
                Intent intent = new Intent(EditCarView.this, CarListingsView.class);
                Toast.makeText(this, "Car updated successfully!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}