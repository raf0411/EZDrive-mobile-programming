package id.ac.binus.myapplication.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import id.ac.binus.myapplication.models.Booking;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EZDriveDB";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "Users";
    public static final String TABLE_CARS = "Cars";
    public static final String TABLE_BOOKINGS = "Bookings";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    "userId TEXT PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "password TEXT UNIQUE NOT NULL)";

    private static final String CREATE_TABLE_CARS =
            "CREATE TABLE " + TABLE_CARS + " (" +
                    "carId TEXT PRIMARY KEY, " +
                    "hostName TEXT NOT NULL," +
                    "location TEXT NOT NULL, " +
                    "description TEXT NOT NULL," +
                    "seats INT NOT NULL," +
                    "transmission TEXT NOT NULL, " +
                    "rules TEXT NOT NULL, " +
                    "carModel TEXT NOT NULL, " +
                    "carBrand TEXT NOT NULL, " +
                    "pricePerDay DECIMAL NOT NULL, " +
                    "availability TEXT NOT NULL, " +
                    "carImg INT NOT NULL)";


    private static final String CREATE_TABLE_BOOKINGS =
                "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                "bookingId TEXT PRIMARY KEY, " +
                "userId TEXT NOT NULL, " +
                "carId TEXT NOT NULL, " +
                "startDate TEXT, " +
                "endDate TEXT, " +
                "totalPrice REAL, " +
                "FOREIGN KEY (userId) REFERENCES " + TABLE_USERS + "(userId), " +
                "FOREIGN KEY (carId) REFERENCES " + TABLE_CARS + "(carId))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public long addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", user.getUserId());
        cv.put("username", user.getUsername());
        cv.put("email", user.getEmail());
        cv.put("password", user.getPassword());

        long result = db.insert(TABLE_USERS, null, cv);
        db.close();

        return result;
    }

    public long addCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("carId", car.getCarId());
        cv.put("hostName", car.getHostName());
        cv.put("location", car.getLocation());
        cv.put("description", car.getDescription());
        cv.put("seats", car.getSeats());
        cv.put("transmission", car.getTransmission());
        String rules = String.join(", ", car.getRules());
        cv.put("rules", rules);
        cv.put("carModel", car.getModel());
        cv.put("carBrand", car.getBrand());
        cv.put("pricePerDay", car.getPricePerDay());
        cv.put("availability", car.getAvailability());
        cv.put("carImg", car.getCarImg());

        long result = db.insert(TABLE_CARS, null, cv);
        db.close();

        return result;
    }

    public long editCar(String carId, String carBrand, String carModel, String carHost,
                        int carSeats, String carTransmission, String carLocation,
                        double carPrice, String carDescription, String carRules){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("carBrand", carBrand);
        cv.put("carModel", carModel);
        cv.put("hostName", carHost);
        cv.put("seats", carSeats);
        cv.put("transmission", carTransmission);
        cv.put("location", carLocation);
        cv.put("pricePerDay", carPrice);
        cv.put("description", carDescription);
        cv.put("rules", carRules);

        long result = db.update("Cars", cv, "carId = ?", new String[]{carId});
        db.close();

        return result;
    }

    public void deleteCar(String carId){
        System.out.println("Deleting");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARS, "carId = ?", new String[]{carId});
        db.close();
    }


    public long addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDateFormatted = sdf.format(booking.getStartDate());
        String endDateFormatted = sdf.format(booking.getEndDate());

        cv.put("startDate", startDateFormatted);
        cv.put("endDate", endDateFormatted);
        cv.put("userId", booking.getUserId());
        cv.put("totalPrice", booking.getTotalPrice());
        cv.put("carId", booking.getCarid());
        cv.put("bookingId", booking.getBookingId());

        long result = db.insert(TABLE_BOOKINGS, null, cv);
        db.close();

        return result;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);

        if(cursor.moveToFirst()){
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow("userId"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                users.add(new User(userId, username, email, password));
            } while (cursor.moveToNext());
        }

        return users;
    }

    public ArrayList<Car> getAllCars(){
        ArrayList<Car> cars = new ArrayList<Car>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cars", null);

        if(cursor.moveToFirst()){
            do {
                String carId = cursor.getString(cursor.getColumnIndexOrThrow("carId"));
                int carImg = cursor.getInt(cursor.getColumnIndexOrThrow("carImg"));
                String carBrand = cursor.getString(cursor.getColumnIndexOrThrow("carBrand"));
                String carModel = cursor.getString(cursor.getColumnIndexOrThrow("carModel"));
                String hostName = cursor.getString(cursor.getColumnIndexOrThrow("hostName"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int seats = cursor.getInt(cursor.getColumnIndexOrThrow("seats"));
                String transmission = cursor.getString(cursor.getColumnIndexOrThrow("transmission"));
                double pricePerDay = cursor.getDouble(cursor.getColumnIndexOrThrow("pricePerDay"));
                String availability = cursor.getString(cursor.getColumnIndexOrThrow("availability"));
                String rules = cursor.getString(cursor.getColumnIndexOrThrow("rules"));

                ArrayList<String> convertedRules = new ArrayList<>(Arrays.asList(rules.split(",")));

                cars.add(new Car(carImg, carId, carBrand, hostName, location, description, seats, transmission, carModel, pricePerDay, availability, convertedRules));
            } while (cursor.moveToNext());
        }

        return cars;
    }

    public void updateCarStatus(String carId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("availability", "Not Available");
        db.update("Cars", cv, "carId = ?", new String[]{carId});
        System.out.println("Car Status Updated!");
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) { // Adjust based on the changes
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
            db.execSQL(CREATE_TABLE_CARS);
        }
    }

}
