package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.UserController;

public class LoginView extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginBtn;
    TextView registerLink;
    UserController userController;
    TextView inputErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userController = new UserController();
        inputErrorText = findViewById(R.id.inputErrorText);
        loginBtn = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegisPage = new Intent(LoginView.this, RegisterView.class);
                startActivity(toRegisPage);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameEditText = findViewById(R.id.usernameEditText);
                passwordEditText = findViewById(R.id.passwordEditText);
                String username = usernameEditText.getText().toString();
                String message = userController.validateUserLogin(LoginView.this, username, passwordEditText.getText().toString());

                inputErrorText.setText(message);

                if(message.equals("Login Success!")){
                    String userId = userController.getUserByUsername(LoginView.this, username).getUserId();

                    getSharedPreferences("EZDriveApp", MODE_PRIVATE)
                            .edit()
                            .putString("userId", userId)
                            .apply();

                    Intent intent = new Intent(LoginView.this, CarListingsView.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });
    }
}