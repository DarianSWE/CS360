package com.example.cs360dariangernandeventtrackingappoption2projectone;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class EventDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // Event table
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Event table
        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(CREATE_EVENT_TABLE);

        // User table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ------------------ Event CRUD ------------------
    public void insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_DATE, event.getDate());
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                eventList.add(new Event(name, date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }

    public void deleteEvent(String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_NAME + "=? AND " + COLUMN_DATE + "=?", new String[]{name, date});
        db.close();
    }

    // ------------------ User Auth Logic ------------------

    // Add new user
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // if insert was successful
    }

    // Check login credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
}


