package id.ac.binus.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EZDRIVE_DB";
    private static final int DATABASE_VERSION = 1;

    // Define your table helpers here
    private UsersTableHelper usersTableHelper;
    private CarsTableHelper carsTableHelper;
    private BookingsTableHelper bookingsTableHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        usersTableHelper = new UsersTableHelper(context);
        carsTableHelper = new CarsTableHelper(context);
        bookingsTableHelper = new BookingsTableHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        usersTableHelper.onCreate(db);
        carsTableHelper.onCreate(db);
        bookingsTableHelper.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        usersTableHelper.onUpgrade(db, oldVersion, newVersion);
        carsTableHelper.onUpgrade(db, oldVersion, newVersion);
        bookingsTableHelper.onUpgrade(db, oldVersion, newVersion);
    }
}
