package com.example.blooddonor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDonorActivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etAge, etAddress;
    Spinner spinnerBloodGroup, spinnerGender;
    Button btnSave;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        db = new DatabaseHelper(this);

        etName    = findViewById(R.id.etName);
        etPhone   = findViewById(R.id.etPhone);
        etEmail   = findViewById(R.id.etEmail);
        etAge     = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerGender     = findViewById(R.id.spinnerGender);
        btnSave   = findViewById(R.id.btnSave);

        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, bloodGroups));

        String[] genders = {"Male", "Female", "Other"};
        spinnerGender.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genders));

        btnSave.setOnClickListener(v -> {
            String name       = etName.getText().toString().trim();
            String phone      = etPhone.getText().toString().trim();
            String email      = etEmail.getText().toString().trim();
            String age        = etAge.getText().toString().trim();
            String address    = etAddress.getText().toString().trim();
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
            String gender     = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || phone.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Name, phone and age are required", Toast.LENGTH_SHORT).show();
                return;
            }

            FirestoreHelper firestoreHelper = new FirestoreHelper();
            firestoreHelper.addDonor(name, phone, email, bloodGroup, age, gender, address);
            Toast.makeText(this, "Donor added successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
