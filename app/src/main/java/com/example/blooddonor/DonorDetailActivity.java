package com.example.blooddonor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DonorDetailActivity extends AppCompatActivity {

    TextView tvName, tvBloodGroup, tvPhone, tvEmail, tvAge, tvGender, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_detail);

        tvName       = findViewById(R.id.tvName);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvPhone      = findViewById(R.id.tvPhone);
        tvEmail      = findViewById(R.id.tvEmail);
        tvAge        = findViewById(R.id.tvAge);
        tvGender     = findViewById(R.id.tvGender);
        tvAddress    = findViewById(R.id.tvAddress);

        // Read all data passed from the list screen
        tvName.setText(getIntent().getStringExtra("donor_name"));
        tvBloodGroup.setText(getIntent().getStringExtra("donor_blood"));
        tvPhone.setText(getIntent().getStringExtra("donor_phone"));
        tvEmail.setText(getIntent().getStringExtra("donor_email"));
        tvAge.setText(getIntent().getStringExtra("donor_age"));
        tvGender.setText(getIntent().getStringExtra("donor_gender"));
        tvAddress.setText(getIntent().getStringExtra("donor_address"));
    }
}
