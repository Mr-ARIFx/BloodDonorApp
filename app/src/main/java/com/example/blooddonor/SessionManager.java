package com.example.blooddonor;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "BloodDonorsSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BLOOD_GROUP = "bloodGroup";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createSession(String username, String email, String password,
                               String bloodGroup, String phone, String address,
                               String age, String gender) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_BLOOD_GROUP, bloodGroup);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_AGE, age);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public String getUsername() { return pref.getString(KEY_USERNAME, ""); }
    public String getEmail()    { return pref.getString(KEY_EMAIL, ""); }
    public String getPassword() { return pref.getString(KEY_PASSWORD, ""); }
    public String getBloodGroup() { return pref.getString(KEY_BLOOD_GROUP, ""); }
    public String getPhone()    { return pref.getString(KEY_PHONE, ""); }
    public String getAddress()  { return pref.getString(KEY_ADDRESS, ""); }
    public String getAge()      { return pref.getString(KEY_AGE, ""); }
    public String getGender()   { return pref.getString(KEY_GENDER, ""); }

    public void updateProfile(String phone, String address, String age, String gender) {
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_AGE, age);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public void logout() {
        // Only clear the logged in status, not the user credentials
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }
}
