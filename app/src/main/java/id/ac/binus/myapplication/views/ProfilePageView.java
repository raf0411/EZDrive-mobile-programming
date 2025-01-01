package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.UserController;
import id.ac.binus.myapplication.models.User;

public class ProfilePageView extends AppCompatActivity {
    ImageButton backBtn;
    ImageView profileImg;
    EditText usernameEditText, emailEditText, phoneNumberEditText, cityEditText, countryEditText;
    Button editProfileBtn, logoutBtn;

    UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("EZDriveApp", MODE_PRIVATE);
        String userId = prefs.getString("userId", "NONE");

        User user = userController.getUserByUserId(this, userId);
        byte[] userImg = user.getUserImg();
        String username = user.getUsername();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String city = user.getCity();
        String country = user.getCountry();

        backBtn = findViewById(R.id.backBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        profileImg = findViewById(R.id.profileImg);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        cityEditText = findViewById(R.id.cityEditText);
        countryEditText = findViewById(R.id.countryEditText);

        profileImg.setImageBitmap(getBitmapFromBytes(userImg));
        usernameEditText.setText(username);
        emailEditText.setText(email);
        phoneNumberEditText.setText(phoneNumber);
        cityEditText.setText(city);
        countryEditText.setText(country);

        if (username.equalsIgnoreCase("admin")){
            editProfileBtn.setVisibility(View.GONE);
        }

        editProfileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePageView.this, EditProfileView.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePageView.this, LoginView.class);
            Toast.makeText(ProfilePageView.this, "User " + username + " has logout!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        backBtn.setOnClickListener(view -> finish());
    }

    private Bitmap getBitmapFromBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}