package com.example.blooddonor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {

    private List<Donor> donorList;

    // Interface so the Activity handles the click
    public interface OnDonorClickListener {
        void onDonorClick(Donor donor);
    }

    private OnDonorClickListener listener;

    public DonorAdapter(List<Donor> donorList, OnDonorClickListener listener) {
        this.donorList = donorList;
        this.listener  = listener;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donor, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.tvName.setText(donor.getName());
        holder.tvBloodGroup.setText(donor.getBloodGroup());
        holder.tvPhone.setText(donor.getPhone());
        holder.tvAddress.setText(donor.getAddress());

        // Open detail screen on row click
        holder.itemView.setOnClickListener(v -> listener.onDonorClick(donor));
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public void updateList(List<Donor> newList) {
        donorList = newList;
        notifyDataSetChanged();
    }

    static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvBloodGroup, tvPhone, tvAddress;

        DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName       = itemView.findViewById(R.id.tvDonorName);
            tvBloodGroup = itemView.findViewById(R.id.tvDonorBloodGroup);
            tvPhone      = itemView.findViewById(R.id.tvDonorPhone);
            tvAddress    = itemView.findViewById(R.id.tvDonorAddress);
        }
    }
}
