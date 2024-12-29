package id.ac.binus.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import id.ac.binus.myapplication.views.LoginView;

public class MainActivity extends AppCompatActivity {

    Button startNowBtn;

    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if(isGranted){
                    Log.d("FirebaseDebug", "Notification Permission Granted.");
                    getDeviceToken();
                } else{
                    Log.e("FirebaseDebug", "Notification Permission Denied.");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        requestPermission();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startNowBtn = findViewById(R.id.startNowBtn);

        startNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginPage = new Intent(MainActivity.this, LoginView.class);
                startActivity(toLoginPage);
            }
        });
    }

    public void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                System.out.println("Permission Already granted");
                getDeviceToken();
            } else if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
                // Explain to user
            } else{
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else{
            getDeviceToken();
        }
    }

    public void getDeviceToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("FirebaseDebug", "Fetching FCM Token Failed ", task.getException());
                return;
            } else{
                String token = task.getResult();
                getSharedPreferences("EZDriveApp", MODE_PRIVATE)
                        .edit()
                        .putString("token", token)
                        .apply();
                Log.d("FirebaseDebug", "FCM Token: " + token);
            }
        });
    }
}