package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonorListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DonorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize with empty list to avoid null issues
        adapter = new DonorAdapter(new ArrayList<>(), donor -> {
            Intent intent = new Intent(DonorListActivity.this, DonorDetailActivity.class);
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

        FirestoreHelper firestoreHelper = new FirestoreHelper();
        firestoreHelper.getAllDonors(donors -> {
            adapter.updateList(donors);
        });
    }
}
