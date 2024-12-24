package id.ac.binus.myapplication.views;

import android.os.Bundle;
import android.widget.TextView;

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
import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.models.Car;

public class CarListingsView extends AppCompatActivity {
    TextView usernameTV;
    RecyclerView carRecyclerView;
    DatabaseHelper databaseHelper;

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

        String username = getIntent().getStringExtra("username");
        usernameTV = findViewById(R.id.usernameTV);
        usernameTV.setText(username);

        carRecyclerView = findViewById(R.id.carRecyclerView);
        ArrayList<Car> cars = new ArrayList<Car>();
        databaseHelper = new DatabaseHelper(this);

        cars.add(new Car(R.drawable.tesla, "TS001", "Tesla", "C001", 500000, true));

        carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carRecyclerView.setAdapter(new CarAdapter(this, cars));
    }
}