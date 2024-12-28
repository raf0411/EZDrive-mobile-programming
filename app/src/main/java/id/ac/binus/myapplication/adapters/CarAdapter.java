package id.ac.binus.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.viewHolders.CarViewHolder;
import id.ac.binus.myapplication.views.EditCarView;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private ArrayList<Car> cars;
    private Context context;
    private String username;
    private OnCarDeletedListener onCarDeletedListener;

    public CarAdapter(ArrayList<Car> cars, Context context, String username) {
        this.cars = cars;
        this.context = context;
        this.username = username;
    }

    public interface OnCarDeletedListener {
        void onCarDeleted(int position);
    }

    public void setOnCarDeletedListener(OnCarDeletedListener listener) {
        this.onCarDeletedListener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view, context, username);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);

        holder.carImg.setImageResource(car.getCarImg());
        holder.carBrand.setText(car.getBrand());
        holder.carPricePerDay.setText("From Rp. " + car.getPricePerDay() + "/day");
        holder.availability.setText(car.getAvailability());
        holder.bind(car);

        holder.deleteCarBtn.setOnClickListener(view -> {
            if (onCarDeletedListener != null) {
                onCarDeletedListener.onCarDeleted(holder.getAdapterPosition());
            }
        });

        holder.editCarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditCarView.class);

            intent.putExtra("carId", car.getCarId());
            intent.putExtra("carImg", car.getCarImg());
            intent.putExtra("carBrand", car.getBrand());
            intent.putExtra("carModel", car.getModel());
            intent.putExtra("carHost", car.getHostName());
            intent.putExtra("carLocation", car.getLocation());
            intent.putExtra("carPricePerDay", car.getPricePerDay());
            intent.putExtra("carDescription", car.getDescription());
            intent.putExtra("carSeats", car.getSeats());
            intent.putExtra("carTransmission", car.getTransmission());
            intent.putExtra("carRules", car.getRules());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
