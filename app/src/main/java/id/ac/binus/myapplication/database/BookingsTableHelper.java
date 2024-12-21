package id.ac.binus.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingsTableHelper extends SQLiteOpenHelper {
    private static final String TABLE_TITLE = "Bookings";
    private static final String COL_ID = "bookingId";
    private static final String COL_USER_ID = "userId";
    private static final String COL_CAR_ID = "carId";
    private static final String COL_START_DATE = "startDate";
    private static final String COL_END_DATE = "endDate";
    private static final String COL_TOTAL_PRICE = "totalPrice";

    public BookingsTableHelper(Context context) {
        super(context, "EZDRIVE_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblQuery = "CREATE TABLE " + TABLE_TITLE + " (" +
                COL_ID + " VARCHAR(10) PRIMARY KEY, " +
                COL_USER_ID + " VARCHAR(10), " +
                COL_CAR_ID + " VARCHAR(10), " +
                COL_START_DATE + " DATE, " +
                COL_END_DATE + " DATE, " +
                COL_TOTAL_PRICE + " DECIMAL, " +
                "FOREIGN KEY (" + COL_USER_ID + ") REFERENCES Users(userId), " +
                "FOREIGN KEY (" + COL_CAR_ID + ") REFERENCES Cars(carId))";
        db.execSQL(createTblQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        onCreate(db);
    }
}
