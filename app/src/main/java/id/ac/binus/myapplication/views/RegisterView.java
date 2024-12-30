package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.UserController;

public class RegisterView extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, confirmPasswordEditText, emailEditText;
    Button registerBtn;
    TextView errorLbl;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(view -> {
            userController = new UserController();
            errorLbl = findViewById(R.id.registerErrorLbl);
            usernameEditText = findViewById(R.id.usernameEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
            emailEditText = findViewById(R.id.emailEditText);
            String username = usernameEditText.getText().toString();
            String message = userController.validateUserRegister(view.getContext(), username, emailEditText.getText().toString(), passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());

            errorLbl.setText(message);

            Log.d("tag", "Checking Username: " + username);

            if(message.equals("User registered successfully!")){
                errorLbl.setTextColor(Color.parseColor("#00FF00"));
                String userId = userController.getUserByUsername(RegisterView.this, username).getUserId();

                getSharedPreferences("EZDriveApp", MODE_PRIVATE)
                        .edit()
                        .putString("userId", userId)
                        .apply();

                getSharedPreferences("EZDriveApp", MODE_PRIVATE)
                        .edit()
                        .putString("username", username)
                        .apply();

                Intent intent = new Intent(RegisterView.this, CarListingsView.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}