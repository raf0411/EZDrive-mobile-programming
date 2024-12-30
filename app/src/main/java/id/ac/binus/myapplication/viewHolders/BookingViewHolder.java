package id.ac.binus.myapplication.viewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.controllers.CarController;
import id.ac.binus.myapplication.models.Booking;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.views.BookingDetailsView;

public class BookingViewHolder extends RecyclerView.ViewHolder{
    public ImageView carImg;
    public TextView carName;
    public TextView carPricePerDay;
    public TextView carBookStartDate;
    public TextView carBookEndDate;
    public TextView carBookTotalPrice;
    CarController carController = new CarController();

    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);

        this.carImg = itemView.findViewById(R.id.carBookingImgData);
        this.carName = itemView.findViewById(R.id.carBookingNameData);
        this.carPricePerDay = itemView.findViewById(R.id.carBookingPriceData);
        this.carBookStartDate = itemView.findViewById(R.id.carBookStartDate);
        this.carBookEndDate = itemView.findViewById(R.id.carBookEndDate);
        this.carBookTotalPrice = itemView.findViewById(R.id.carBookTotalPrice);
    }

    @SuppressLint("SetTextI18n")
    public void bind(Booking booking, Context context){
        Car car = carController.getCarByCarId(context, booking.getCarId());

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), BookingDetailsView.class);
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

            itemView.getContext().startActivity(intent);
        });
    }
}
