package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvEmail, tvBloodGroup, tvPhone, tvAddress, tvAge, tvGender;
    Button btnEditProfile, btnLogout;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);

        tvUsername   = findViewById(R.id.tvUsername);
        tvEmail      = findViewById(R.id.tvEmail);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvPhone      = findViewById(R.id.tvPhone);
        tvAddress    = findViewById(R.id.tvAddress);
        tvAge        = findViewById(R.id.tvAge);
        tvGender     = findViewById(R.id.tvGender);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout      = findViewById(R.id.btnLogout);

        loadProfile();

        btnEditProfile.setOnClickListener(v ->
                startActivity(new Intent(this, EditProfileActivity.class)));

        btnLogout.setOnClickListener(v -> {
            session.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadProfile() {
        tvUsername.setText(session.getUsername());
        tvEmail.setText(session.getEmail());
        tvBloodGroup.setText(session.getBloodGroup());
        tvPhone.setText(session.getPhone());
        tvAddress.setText(session.getAddress());
        tvAge.setText(session.getAge());
        tvGender.setText(session.getGender());
    }

    // Refresh when returning from EditProfile
    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
    }
}
