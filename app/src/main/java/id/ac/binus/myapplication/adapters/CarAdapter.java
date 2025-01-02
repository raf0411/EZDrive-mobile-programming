package id.ac.binus.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.viewHolders.CarViewHolder;
import id.ac.binus.myapplication.views.EditCarView;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private final List<Car> cars;
    private final Context context;
    private final String username;
    private OnCarDeletedListener onCarDeletedListener;

    public CarAdapter(List<Car> cars, Context context, String username) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view, username);
    }

    private Bitmap getBitmapFromBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private String saveBitmapToInternalStorage(Bitmap bitmap) {
        try {
            File file = new File(context.getFilesDir(), "car_image_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        String carName = car.getBrand() + " " + car.getModel();
        String carPrice = "From Rp. " + car.getPricePerDay() + "/day";
        Bitmap carImg = getBitmapFromBytes(car.getCarImg());

        holder.carImg.setImageBitmap(carImg);
        holder.carBrand.setText(carName);
        holder.carPricePerDay.setText(carPrice);
        holder.availability.setText(car.getAvailability());

        if (holder.availability.getText().equals("Not Available")) {
            holder.availability.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.availability.setTextColor(Color.parseColor("#00FF00")); // Optional
        }

        holder.bind(car);

        holder.deleteCarBtn.setOnClickListener(view -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && onCarDeletedListener != null) {
                onCarDeletedListener.onCarDeleted(currentPosition);
            }
        });

        holder.editCarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditCarView.class);

            intent.putExtra("carId", car.getCarId());
            String carImgPath = saveBitmapToInternalStorage(carImg);
            intent.putExtra("carImgPath", carImgPath);
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
