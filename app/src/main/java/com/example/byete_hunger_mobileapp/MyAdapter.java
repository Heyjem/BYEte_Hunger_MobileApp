package com.example.byete_hunger_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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
        donation Donation = list.get(position);

        holder.type.setText(Donation.getType());
        holder.weight.setText(Donation.getWeight());
        holder.datePurchased.setText(Donation.getDatePurchased());
        holder.dateExpired.setText(Donation.getDateExpired());
        holder.notes.setText(Donation.getNotes());
        holder.id.setText(Donation.getId());
        holder.dateAdded.setText(Donation.getDateAdded());
        holder.dateAddedTime.setText(Donation.getDateAddedTime());
        //Glide.with(context).load(Donation.getUrl()).into(holder.donationCardImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, weight, datePurchased, dateExpired, notes, id, dateAdded, dateAddedTime;
        Button track;
        CardView cardView;
        ImageView donationCardImage;

        RelativeLayout donationcardcontent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.tv_donationcard_typedesc);
            weight = itemView.findViewById(R.id.tv_donationcard_weightdesc);
            datePurchased = itemView.findViewById(R.id.tv_donationcard_dateofpurchasedesc);
            dateExpired = itemView.findViewById(R.id.tv_donationcard_expirationdatedesc);
            notes = itemView.findViewById(R.id.tv_donationcard_notesdesc);
            id = itemView.findViewById(R.id.tv_donationcard_uidCode);
            dateAdded = itemView.findViewById(R.id.tv_donationcard_dateadded);
            dateAddedTime = itemView.findViewById(R.id.tv_donationcard_dateaddedTime);
            donationCardImage = itemView.findViewById(R.id.iV_donationcard);

            track = itemView.findViewById(R.id.btn_donationcard_track);

            cardView = itemView.findViewById(R.id.cv_donationCard);
            donationcardcontent = itemView.findViewById(R.id.rl_donationcard_content);


            track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(cardView.getContext(),statustracker.class);
                    cardView.getContext().startActivity(intent);
                }
            });

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

