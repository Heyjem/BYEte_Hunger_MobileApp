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
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.donation_card, parent, false);
        return new MyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.type.setText(list.get(position).getType());
        holder.weight.setText(list.get(position).getWeight());
        holder.datePurchased.setText(list.get(position).getDatePurchased());
        holder.dateExpired.setText(list.get(position).getDateExpired());
        holder.notes.setText(list.get(position).getNotes());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type, weight, datePurchased, dateExpired, notes;
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
