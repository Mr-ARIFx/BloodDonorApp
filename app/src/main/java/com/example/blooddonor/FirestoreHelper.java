package com.example.blooddonor;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreHelper {

    private FirebaseFirestore db;
    private static final String COLLECTION = "donors";

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Interface to return results asynchronously
    public interface OnDonorsLoadedListener {
        void onLoaded(List<Donor> donors);
    }

    // Add a donor to Firestore
    public void addDonor(String name, String phone, String email,
                         String bloodGroup, String age,
                         String gender, String address) {
        Map<String, Object> donor = new HashMap<>();
        donor.put("name",       name);
        donor.put("phone",      phone);
        donor.put("email",      email);
        donor.put("bloodGroup", bloodGroup);
        donor.put("age",        age);
        donor.put("gender",     gender);
        donor.put("address",    address);

        db.collection(COLLECTION).add(donor);
    }

    // Get all donors
    public void getAllDonors(OnDonorsLoadedListener listener) {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(snapshots -> {
                    List<Donor> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        list.add(documentToDonor(doc));
                    }
                    listener.onLoaded(list);
                });
    }

    // Search by blood group
    public void getDonorsByBloodGroup(String bloodGroup,
                                      OnDonorsLoadedListener listener) {
        db.collection(COLLECTION)
                .whereEqualTo("bloodGroup", bloodGroup)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Donor> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        list.add(documentToDonor(doc));
                    }
                    listener.onLoaded(list);
                });
    }

    // Search by blood group AND location
    public void getDonorsByBloodGroupAndLocation(String bloodGroup, String location,
                                                 OnDonorsLoadedListener listener) {
        // Firestore does not support LIKE, so filter location on device side
        db.collection(COLLECTION)
                .whereEqualTo("bloodGroup", bloodGroup)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Donor> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        String address = doc.getString("address");
                        if (address != null &&
                                address.toLowerCase().contains(location.toLowerCase())) {
                            list.add(documentToDonor(doc));
                        }
                    }
                    listener.onLoaded(list);
                });
    }

    // Convert Firestore document to Donor object
    private Donor documentToDonor(QueryDocumentSnapshot doc) {
        return new Donor(
                0,
                doc.getString("name"),
                doc.getString("phone"),
                doc.getString("email"),
                doc.getString("bloodGroup"),
                doc.getString("age"),
                doc.getString("gender"),
                doc.getString("address")
        );
    }
}