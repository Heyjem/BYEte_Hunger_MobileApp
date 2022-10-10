package com.example.byete_hunger_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

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
        //holder.uploadImage.setImageDrawable(list.get(position).getUploadImage());
        holder.id.setText(list.get(position).getId());
        holder.dateAdded.setText(list.get(position).getDateAdded());
        holder.dateAddedTime.setText(list.get(position).getDateAddedTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, weight, datePurchased, dateExpired, notes, id, dateAdded, dateAddedTime;
        CardView cardView;
        ImageView uploadImage;
        RelativeLayout donationcardcontent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tv_donationcard_typedesc);
            weight = itemView.findViewById(R.id.tv_donationcard_weightdesc);
            datePurchased = itemView.findViewById(R.id.tv_donationcard_dateofpurchasedesc);
            dateExpired = itemView.findViewById(R.id.tv_donationcard_expirationdatedesc);
            notes = itemView.findViewById(R.id.tv_donationcard_notesdesc);
            uploadImage = itemView.findViewById(R.id.donate_uploadImage);
            cardView = itemView.findViewById(R.id.cv_donationCard);
            donationcardcontent = itemView.findViewById(R.id.rl_donationcard_content);
            id = itemView.findViewById(R.id.tv_donationcard_uidCode);
            dateAdded = itemView.findViewById(R.id.tv_donationcard_dateadded);
            dateAddedTime = itemView.findViewById(R.id.tv_donationcard_dateaddedTime);

            // donation card expands and collapses
            cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (donationcardcontent.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.VISIBLE);
                } else if (donationcardcontent.getVisibility() != View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.GONE);
                }
            }
        });
        }
    }
}

