package id.ac.binus.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.viewHolders.CarViewHolder;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {
    private Context context;
    private ArrayList<Car> cars;
    private String username; // Add username field

    public CarAdapter(Context context, ArrayList<Car> cars, String username) {
        this.context = context;
        this.cars = cars;
        this.username = username; // Assign username
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view, context, username); // Pass username to ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.bind(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
