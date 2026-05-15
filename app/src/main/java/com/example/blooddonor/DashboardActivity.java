package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnDonorList, btnAddDonor, btnSearch, btnProfile;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionManager(this);

        tvWelcome   = findViewById(R.id.tvWelcome);
        btnDonorList = findViewById(R.id.btnDonorList);
        btnAddDonor  = findViewById(R.id.btnAddDonor);
        btnSearch    = findViewById(R.id.btnSearch);
        btnProfile   = findViewById(R.id.btnProfile);

        tvWelcome.setText("Welcome, " + session.getUsername());

        btnDonorList.setOnClickListener(v ->
                startActivity(new Intent(this, DonorListActivity.class)));

        btnAddDonor.setOnClickListener(v ->
                startActivity(new Intent(this, AddDonorActivity.class)));

        btnSearch.setOnClickListener(v ->
                startActivity(new Intent(this, SearchActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }
}
