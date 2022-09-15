package com.example.byete_hunger_mobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<donation> list;

    public MyAdapter(Context context, ArrayList<donation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.donation_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        donation Donation = list.get(position);
        holder.type.setTag(Donation.getType());
        holder.weight.setText(Donation.getWeight());
        holder.datePurchased.setText(Donation.getDatePurchased());
        holder.dateExpired.setText(Donation.getDateExpired());
        holder.notes.setText(Donation.getNotes());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Spinner type;
        TextView weight, datePurchased, dateExpired, notes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tv_donationcard_typedesc);
            weight = itemView.findViewById(R.id.tv_donationcard_weightdesc);
            datePurchased = itemView.findViewById(R.id.tv_donationcard_dateofpurchasedesc);
            dateExpired = itemView.findViewById(R.id.tv_donationcard_expirationdatedesc);
            notes = itemView.findViewById(R.id.tv_donationcard_notesdesc);
        }
    }
}
