package id.ac.binus.myapplication.viewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    private Bitmap getBitmapFromBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @SuppressLint("SetTextI18n")
    public void bind(Booking booking, Context context){
        Car car = carController.getCarByCarId(context, booking.getCarId());
        Bitmap carBitmap = getBitmapFromBytes(car.getCarImg());
        String imagePath = saveImageToInternalStorage(carBitmap);
        System.out.println("Image Path: " + imagePath);

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), BookingDetailsView.class);
            intent.putExtra("carId", car.getCarId());
            intent.putExtra("carImg", imagePath);
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


    private String saveImageToInternalStorage(Bitmap bitmap) {
        Context context = itemView.getContext();
        FileOutputStream fos = null;
        try {
            File file = new File(context.getFilesDir(), "car_image_" + System.currentTimeMillis() + ".jpg");
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Image Error");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("Image Error");
                }
            }
        }
        return null;
    }
}
