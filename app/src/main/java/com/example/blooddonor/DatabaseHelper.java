package com.example.blooddonor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BloodDonors.db";
    private static final int DATABASE_VERSION = 2; // Incremented version

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_BLOOD = "blood_group";
    public static final String COL_USER_PHONE = "phone";
    public static final String COL_USER_ADDRESS = "address";
    public static final String COL_USER_AGE = "age";
    public static final String COL_USER_GENDER = "gender";

    // Donor table
    public static final String TABLE_DONORS = "donors";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_EMAIL = "email";
    public static final String COL_BLOOD_GROUP = "blood_group";
    public static final String COL_AGE = "age";
    public static final String COL_GENDER = "gender";
    public static final String COL_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USER_NAME + " TEXT, "
                + COL_USER_EMAIL + " TEXT, "
                + COL_USER_PASSWORD + " TEXT, "
                + COL_USER_BLOOD + " TEXT, "
                + COL_USER_PHONE + " TEXT, "
                + COL_USER_ADDRESS + " TEXT, "
                + COL_USER_AGE + " TEXT, "
                + COL_USER_GENDER + " TEXT)";
        db.execSQL(createUsersTable);

        // Create Donors table
        String createDonorsTable = "CREATE TABLE " + TABLE_DONORS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_PHONE + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_BLOOD_GROUP + " TEXT, "
                + COL_AGE + " TEXT, "
                + COL_GENDER + " TEXT, "
                + COL_ADDRESS + " TEXT)";
        db.execSQL(createDonorsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONORS);
        onCreate(db);
    }

    // User Methods
    public boolean registerUser(String username, String email, String password,
                                String blood, String phone, String address, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, username);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);
        values.put(COL_USER_BLOOD, blood);
        values.put(COL_USER_PHONE, phone);
        values.put(COL_USER_ADDRESS, address);
        values.put(COL_USER_AGE, age);
        values.put(COL_USER_GENDER, gender);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public Cursor checkUser(String identifier, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Check if identifier is either username or email
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE (" + 
                COL_USER_NAME + " = ? OR " + COL_USER_EMAIL + " = ?) AND " + 
                COL_USER_PASSWORD + " = ?", new String[]{identifier, identifier, password});
    }

    // Donor Methods
    public boolean addDonor(String name, String phone, String email,
                            String bloodGroup, String age, String gender, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_EMAIL, email);
        values.put(COL_BLOOD_GROUP, bloodGroup);
        values.put(COL_AGE, age);
        values.put(COL_GENDER, gender);
        values.put(COL_ADDRESS, address);
        long result = db.insert(TABLE_DONORS, null, values);
        return result != -1;
    }

    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DONORS, null);
        if (cursor.moveToFirst()) {
            do {
                donors.add(new Donor(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return donors;
    }
}
