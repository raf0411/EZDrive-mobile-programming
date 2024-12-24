package id.ac.binus.myapplication.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.views.CarDetailsView;

public class CarViewHolder extends RecyclerView.ViewHolder {
    public ImageView carImg;
    public TextView carBrand;
    public TextView carPricePerDay;
    public TextView availability;
    private DatabaseHelper databaseHelper;

    public CarViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.carImg = itemView.findViewById(R.id.carImgData);
        this.carBrand = itemView.findViewById(R.id.carNameData);
        this.carPricePerDay = itemView.findViewById(R.id.carPriceData);
        this.availability = itemView.findViewById(R.id.carAvailabilityData);
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void bind(Car car) {
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), CarDetailsView.class);
            itemView.getContext().startActivity(intent);
        });
    }
}
