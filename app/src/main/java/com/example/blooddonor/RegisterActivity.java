package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etRePassword, etPhone, etAddress, etAge;
    Spinner spinnerBloodGroup, spinnerGender;
    Button btnRegister;
    TextView tvGoToLogin;

    SessionManager session;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(this);
        db = new DatabaseHelper(this);

        etUsername    = findViewById(R.id.etUsername);
        etEmail       = findViewById(R.id.etEmail);
        etPassword    = findViewById(R.id.etPassword);
        etRePassword  = findViewById(R.id.etRePassword);
        etPhone       = findViewById(R.id.etPhone);
        etAddress     = findViewById(R.id.etAddress);
        etAge         = findViewById(R.id.etAge);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerGender     = findViewById(R.id.spinnerGender);
        btnRegister   = findViewById(R.id.btnRegister);
        tvGoToLogin   = findViewById(R.id.tvGoToLogin);

        // Blood group options
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, bloodGroups));

        // Gender options
        String[] genders = {"Male", "Female", "Other"};
        spinnerGender.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genders));

        btnRegister.setOnClickListener(v -> {
            String username   = etUsername.getText().toString().trim();
            String email      = etEmail.getText().toString().trim();
            String password   = etPassword.getText().toString().trim();
            String rePassword = etRePassword.getText().toString().trim();
            String phone      = etPhone.getText().toString().trim();
            String address    = etAddress.getText().toString().trim();
            String age        = etAge.getText().toString().trim();
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
            String gender     = spinnerGender.getSelectedItem().toString();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()
                    || rePassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(rePassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user to SQLite Database
            boolean registered = db.registerUser(username, email, password, bloodGroup, phone, address, age, gender);

            if (registered) {
                // Set current session
                session.createSession(username, email, password, bloodGroup,
                        phone, address, age, gender);

                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        });

        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}
