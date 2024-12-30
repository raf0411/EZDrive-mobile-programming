package id.ac.binus.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.adapters.CarAdapter;
import id.ac.binus.myapplication.controllers.CarController;
import id.ac.binus.myapplication.controllers.UserController;
import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.models.User;

public class CarListingsView extends AppCompatActivity {
    TextView usernameTV, notAvailableTV;
    RecyclerView carRecyclerView;
    ImageButton bookingHistoryBtn, addCarBtn;
    UserController userController = new UserController();
    CarController carController = new CarController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_listings_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameTV = findViewById(R.id.usernameTV);
        notAvailableTV = findViewById(R.id.notAvailableTV);
        addCarBtn = findViewById(R.id.addCarBtn);
        bookingHistoryBtn = findViewById(R.id.bookingHistoryBtn);
        carRecyclerView = findViewById(R.id.carRecyclerView);

        notAvailableTV.setVisibility(View.GONE);

        String username = getIntent().getStringExtra("username");
        User user = userController.getUserByUsername(CarListingsView.this, username);
        usernameTV.setText(username);

        if (!user.getUsername().equalsIgnoreCase("admin")) {
            addCarBtn.setVisibility(View.GONE);
        }

        ArrayList<Car> cars = carController.getCarsByRole(this, user.getUsername());

        if (cars == null || cars.isEmpty()) {
            carRecyclerView.setVisibility(View.GONE);
            notAvailableTV.setVisibility(View.VISIBLE);
        } else {
            carRecyclerView.setVisibility(View.VISIBLE);
            notAvailableTV.setVisibility(View.GONE);

            CarAdapter carAdapter = new CarAdapter(new ArrayList<>(cars), this, username);
            carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            carRecyclerView.setAdapter(carAdapter);

            carAdapter.setOnCarDeletedListener(position -> {
                carController.deleteCar(this, cars.get(position).getCarId());
                cars.remove(position);
                carAdapter.notifyItemRemoved(position);
                Toast.makeText(this, "Car deleted successfully!", Toast.LENGTH_SHORT).show();
            });
        }

        addCarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CarListingsView.this, AddCarView.class);
            startActivity(intent);
        });

        bookingHistoryBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CarListingsView.this, BookingHistoryView.class);
            startActivity(intent);
        });
    }
}
