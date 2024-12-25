package id.ac.binus.myapplication.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

import id.ac.binus.myapplication.models.Booking;
import id.ac.binus.myapplication.models.Car;
import id.ac.binus.myapplication.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EZDriveDB";
    private static final int DATABASE_VERSION = 1;

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
                    "carId TEXT PRIMARY KEY AUTOINCREMENT, " +
                    "carImg INT NOT NULL," +
                    "hostName TEXT NOT NULL," +
                    "location TEXT NOT NULL, " +
                    "description TEXT NOT NULL," +
                    "seats INT NOT NULL," +
                    "transmission TEXT NOT NULL, " +
                    "rules TEXT NOT NULL, " +
                    "carModel TEXT NOT NULL, " +
                    "carBrand TEXT NOT NULL, " +
                    "pricePerDay DECIMAL NOT NULL, " +
                    "availability TEXT NOT NULL)";

    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    "bookingId TEXT PRIMARY KEY, " +
                    "userId TEXT NOT NULL, " +
                    "carId TEXT NOT NULL, " +
                    "startDate DATE NOT NULL, " +
                    "endDate DATE NOT NULL, " +
                    "totalPrice DECIMAL NOT NULL, " +
                    "FOREIGN KEY(userId) REFERENCES " + TABLE_USERS + "(userId), " +
                    "FOREIGN KEY(carId) REFERENCES " + TABLE_CARS + "(carId));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public long addBooking(Booking booking){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bookingId", booking.getBookingId());
        cv.put("userId", booking.getUserId());
        cv.put("carId", booking.getCarid());

        if (booking.getStartDate() != null) {
            cv.put("startDate", booking.getStartDate().getTime());
        }
        if (booking.getEndDate() != null) {
            cv.put("endDate", booking.getEndDate().getTime());
        }

        cv.put("totalPrice", booking.getTotalPrice());

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
                int seats = cursor.getInt(cursor.getColumnIndexOrThrow("password"));
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
        String sql = "UPDATE Cars SET availability = 'Not Available' WHERE carId = " + carId;

        db.execSQL(sql);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        onCreate(db);
    }
}
