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
    Context context;
    ArrayList<Car> cars;

    public CarAdapter(Context context, ArrayList<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        String carName = cars.get(position).getBrand() + " " + cars.get(position).getModel();
        String pricePerDay = Double.toString(Math.round(cars.get(position).getPricePerDay()));
        String priceMessage = "From Rp. " + pricePerDay + " / day";
        String availability = cars.get(position).getAvailability();

        holder.carImg.setImageResource(cars.get(position).getCarImg());
        holder.carBrand.setText(carName);
        holder.carPricePerDay.setText(priceMessage);
        holder.availability.setText(availability);
        holder.bind(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
