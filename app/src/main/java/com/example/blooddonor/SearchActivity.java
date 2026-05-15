package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Spinner spinnerBloodGroup;
    Button btnSearch;
    RecyclerView recyclerView;
    DonorAdapter adapter;
    DatabaseHelper db;
    EditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new DatabaseHelper(this);

        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        btnSearch         = findViewById(R.id.btnSearch);
        recyclerView      = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etLocation = findViewById(R.id.etLocation);

        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, bloodGroups));

        adapter = new DonorAdapter(new java.util.ArrayList<>(), donor -> {
            Intent intent = new Intent(SearchActivity.this, DonorDetailActivity.class);
            intent.putExtra("donor_id",      donor.getId());
            intent.putExtra("donor_name",    donor.getName());
            intent.putExtra("donor_phone",   donor.getPhone());
            intent.putExtra("donor_email",   donor.getEmail());
            intent.putExtra("donor_blood",   donor.getBloodGroup());
            intent.putExtra("donor_age",     donor.getAge());
            intent.putExtra("donor_gender",  donor.getGender());
            intent.putExtra("donor_address", donor.getAddress());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String selectedGroup = spinnerBloodGroup.getSelectedItem().toString();
            String location      = etLocation.getText().toString().trim();

            FirestoreHelper firestoreHelper = new FirestoreHelper();

            if (location.isEmpty()) {
                firestoreHelper.getDonorsByBloodGroup(selectedGroup,
                        donors -> adapter.updateList(donors));
            } else {
                firestoreHelper.getDonorsByBloodGroupAndLocation(selectedGroup, location,
                        donors -> adapter.updateList(donors));
            }
        });
    }
}
