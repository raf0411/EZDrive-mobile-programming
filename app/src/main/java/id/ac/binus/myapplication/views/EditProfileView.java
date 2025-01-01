package id.ac.binus.myapplication.views;

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
import id.ac.binus.myapplication.controllers.UserController;
import id.ac.binus.myapplication.models.User;

public class EditProfileView extends AppCompatActivity {
    private byte[] selectedImageBytes = null;
    ImageButton backBtn;
    ImageButton editProfileImgBtn;
    EditText usernameEditText, emailEditText, phoneNumberEditText, cityEditText, countryEditText;
    Button updateProfileBtn;
    TextView editProfileErrorLbl;
    UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);
        String userId = prefs.getString("userId", "NONE");

        User user = userController.getUserByUserId(this, userId);
        String username = user.getUsername();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String city = user.getCity();
        String country = user.getCountry();

        backBtn = findViewById(R.id.backBtn);
        editProfileErrorLbl = findViewById(R.id.editProfileErrorLbl);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        editProfileImgBtn = findViewById(R.id.editProfileImgBtn);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        cityEditText = findViewById(R.id.cityEditText);
        countryEditText = findViewById(R.id.countryEditText);

        usernameEditText.setText(username);
        emailEditText.setText(email);
        phoneNumberEditText.setText(phoneNumber);
        cityEditText.setText(city);
        countryEditText.setText(country);

        backBtn.setOnClickListener(view -> finish());

        updateProfileBtn.setOnClickListener(view -> {
            String newUsername = usernameEditText.getText().toString();
            String newEmail = emailEditText.getText().toString();
            String newPhoneNumber = phoneNumberEditText.getText().toString();
            String newCity = cityEditText.getText().toString();
            String newCountry = countryEditText.getText().toString();

            String message = userController.validateUpdateProfile(this, userId, selectedImageBytes, newUsername, newEmail, newPhoneNumber, newCity, newCountry);
            editProfileErrorLbl.setText(message);

            if (message.equals("Profile updated successfully!")) {
                editProfileErrorLbl.setTextColor(Color.parseColor("#454B1B"));
                Intent intent = new Intent(EditProfileView.this, CarListingsView.class);
                intent.putExtra("username", newUsername);
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        editProfileImgBtn.setOnClickListener(view -> {
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
                editProfileImgBtn.setImageBitmap(getBitmapFromBytes(selectedImageBytes));

                Toast.makeText(this, "Profile Image selected successfully!", Toast.LENGTH_SHORT).show();
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