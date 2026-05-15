package com.example.blooddonor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvGoToRegister;

    SessionManager session;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);
        db = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> {
            String identifier = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check SQLite database for user
            Cursor cursor = db.checkUser(identifier, password);

            if (cursor != null && cursor.moveToFirst()) {
                // User found! Get details to start session
                String username   = cursor.getString(1);
                String email      = cursor.getString(2);
                String pass       = cursor.getString(3); // Should match password
                String bloodGroup = cursor.getString(4);
                String phone      = cursor.getString(5);
                String address    = cursor.getString(6);
                String age        = cursor.getString(7);
                String gender     = cursor.getString(8);

                session.createSession(username, email, pass, bloodGroup, phone, address, age, gender);
                
                cursor.close();
                
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            } else {
                if (cursor != null) cursor.close();
                Toast.makeText(this, "Invalid username/email or password", Toast.LENGTH_SHORT).show();
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
