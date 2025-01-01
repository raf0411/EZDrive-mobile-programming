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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.CarController;

public class AddCarView extends AppCompatActivity {

    private byte[] selectedImageBytes = null;
    ImageButton addCarBackBtn, addCarImgBtn;
    EditText carBrandEditText, carModelEditText, carHostEditText, carSeatsEditText,
            carTransmissionEditText, carLocationEditText, carPriceEditText, carDescriptionEditText, carRulesEditText;
    Button addCarBtn;
    CarController carController = new CarController();
    TextView addCarErrorLbl;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_car_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addCarBackBtn = findViewById(R.id.addCarBackBtn);
        addCarImgBtn = findViewById(R.id.addCarImgBtn);
        carBrandEditText = findViewById(R.id.carBrandEditText);
        carModelEditText = findViewById(R.id.carModelEditText);
        carHostEditText = findViewById(R.id.carHostEditText);
        carSeatsEditText = findViewById(R.id.carSeatsEditText);
        carTransmissionEditText = findViewById(R.id.carTransmissionEditText);
        carLocationEditText = findViewById(R.id.carLocationEditText);
        carPriceEditText = findViewById(R.id.carPriceEditText);
        carDescriptionEditText = findViewById(R.id.carDescriptionEditText);
        carRulesEditText = findViewById(R.id.carRulesEditText);
        addCarBtn = findViewById(R.id.addCarBtn);
        addCarErrorLbl = findViewById(R.id.addCarErrorLbl);

        addCarBackBtn.setOnClickListener(view -> finish());

        addCarBtn.setOnClickListener(view -> {
            String carBrand = carBrandEditText.getText().toString();
            String carModel = carModelEditText.getText().toString();
            String carHost = carHostEditText.getText().toString();
            int carSeats = Integer.parseInt(carSeatsEditText.getText().toString());
            String carTransmission = carTransmissionEditText.getText().toString();
            String carLocation = carLocationEditText.getText().toString();
            double carPrice = Double.parseDouble(carPriceEditText.getText().toString());
            String carDescription = carDescriptionEditText.getText().toString();
            String carRules = carRulesEditText.getText().toString();
            SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);

            if (selectedImageBytes == null) {
                addCarErrorLbl.setText("Please select an image for the car!");
                addCarErrorLbl.setTextColor(Color.RED);
                return;
            }

            String message = carController.addCar(AddCarView.this, selectedImageBytes, carBrand, carModel, carHost, carSeats, carTransmission, carLocation, carPrice, carDescription, carRules);
            addCarErrorLbl.setText(message);

            if(addCarErrorLbl.getText().equals("Car added successfully!")){
                addCarErrorLbl.setTextColor(Color.parseColor("#454B1B"));
                Intent intent = new Intent(AddCarView.this, CarListingsView.class);
                intent.putExtra("username", prefs.getString("username", "NONE"));
                Toast.makeText(AddCarView.this, "Add car successful!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        addCarImgBtn.setOnClickListener(view -> {
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
                addCarImgBtn.setImageBitmap(getBitmapFromBytes(selectedImageBytes));

                Toast.makeText(this, "Car Image selected successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
