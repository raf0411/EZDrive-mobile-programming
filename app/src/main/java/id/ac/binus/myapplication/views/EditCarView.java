package id.ac.binus.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.CarController;

public class EditCarView extends AppCompatActivity {

    private byte[] selectedImageBytes = null;
    ImageButton editCarBackBtn, editCarImgBtn;
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
        editCarImgBtn = findViewById(R.id.editCarImgBtn);
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
        double currentPrice = getIntent().getDoubleExtra("carPricePerDay", 0.);
        String currentDesc = getIntent().getStringExtra("carDescription");
        ArrayList<String> currentRules = getIntent().getStringArrayListExtra("carRules");
        String carImgPath = getIntent().getStringExtra("carImgPath");
        if (carImgPath != null) {
            Bitmap carImg = BitmapFactory.decodeFile(carImgPath);
            editCarImgBtn.setImageBitmap(carImg);
        }

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

        editCarBackBtn.setOnClickListener(view -> finish());

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
            SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);

            String message = carController.editCar(EditCarView.this, selectedImageBytes, carId, newCarBrand, newCarModel, newCarHost, newCarSeats, newCarTransmission, newCarLocation, newCarPrice, newCarDescription, newCarRules);
            editCarErrorLbl.setText(message);

            if (message.equals("Car edited successfully!")) {
                editCarErrorLbl.setTextColor(Color.parseColor("#454B1B"));
                Intent intent = new Intent(EditCarView.this, CarListingsView.class);
                intent.putExtra("username", prefs.getString("username", "NONE"));
                Toast.makeText(this, "Car updated successfully!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        editCarImgBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 101);
        });
    }

    private Bitmap getBitmapFromBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                assert imageUri != null;
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                selectedImageBytes = getImageBytes(bitmap);
                editCarImgBtn.setImageBitmap(getBitmapFromBytes(selectedImageBytes));

                Toast.makeText(this, "Car Image selected successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}