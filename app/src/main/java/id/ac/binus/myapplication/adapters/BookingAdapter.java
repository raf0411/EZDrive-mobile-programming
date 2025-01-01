package id.ac.binus.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.models.Booking;
import id.ac.binus.myapplication.viewHolders.BookingViewHolder;

public class BookingAdapter extends RecyclerView.Adapter<BookingViewHolder> {
    private final ArrayList<Booking> bookings;
    private final Context context;

    public BookingAdapter(ArrayList<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    private Bitmap getBitmapFromBytes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Bitmap carImg = getBitmapFromBytes(booking.getCarImg());

        holder.carImg.setImageBitmap(carImg);
        holder.carName.setText(booking.getCarName());
        holder.carPricePerDay.setText("From Rp. " + booking.getCarPricePerDay() + " / day");
        holder.carBookStartDate.setText("From  : " + sdf.format(booking.getStartDate()));
        holder.carBookEndDate.setText("To   : " + sdf.format(booking.getEndDate()));
        holder.carBookTotalPrice.setText("Total Price   : Rp. " + booking.getTotalPrice());
        holder.bind(booking, context);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
