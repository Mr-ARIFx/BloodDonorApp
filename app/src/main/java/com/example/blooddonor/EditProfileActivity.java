package com.example.blooddonor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etPhone, etAddress, etAge;
    Spinner spinnerGender;
    Button btnSave;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        session = new SessionManager(this);

        etPhone   = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etAge     = findViewById(R.id.etAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSave   = findViewById(R.id.btnSave);

        String[] genders = {"Male", "Female", "Other"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genders);
        spinnerGender.setAdapter(genderAdapter);

        // Pre-fill current values
        etPhone.setText(session.getPhone());
        etAddress.setText(session.getAddress());
        etAge.setText(session.getAge());

        // Set spinner to current gender
        String currentGender = session.getGender();
        for (int i = 0; i < genders.length; i++) {
            if (genders[i].equals(currentGender)) {
                spinnerGender.setSelection(i);
                break;
            }
        }

        btnSave.setOnClickListener(v -> {
            String phone   = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String age     = etAge.getText().toString().trim();
            String gender  = spinnerGender.getSelectedItem().toString();

            if (phone.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Phone and age are required", Toast.LENGTH_SHORT).show();
                return;
            }

            session.updateProfile(phone, address, age, gender);
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
