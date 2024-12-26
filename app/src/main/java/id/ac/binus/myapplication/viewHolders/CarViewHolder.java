package id.ac.binus.myapplication.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.models.Car;

public class CarViewHolder extends RecyclerView.ViewHolder {
    public ImageView carImg;
    public TextView carBrand;
    public TextView carPricePerDay;
    public TextView availability;
    public ImageButton editCarBtn;
    public ImageButton deleteCarBtn;

    public CarViewHolder(@NonNull View itemView, Context context, String username) {
        super(itemView);

        this.carImg = itemView.findViewById(R.id.carImgData);
        this.carBrand = itemView.findViewById(R.id.carNameData);
        this.carPricePerDay = itemView.findViewById(R.id.carPriceData);
        this.availability = itemView.findViewById(R.id.carAvailabilityData);

        this.editCarBtn = itemView.findViewById(R.id.editCarBtn);
        this.deleteCarBtn = itemView.findViewById(R.id.deleteCarBtn);

        if (!username.equalsIgnoreCase("Admin")) {
            editCarBtn.setVisibility(View.GONE);
            deleteCarBtn.setVisibility(View.GONE);
        }
    }

    public void bind(Car car) {
        carImg.setImageResource(car.getCarImg());
        carBrand.setText(car.getBrand());
        carPricePerDay.setText("From Rp. " + car.getPricePerDay() + "/day");
        availability.setText(car.getAvailability());
    }
}
