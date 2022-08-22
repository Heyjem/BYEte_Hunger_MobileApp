package com.example.byete_hunger_mobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class donations_tracker extends AppCompatActivity {

    Button donategenerate;
    CardView cardView;
    RelativeLayout donationcardcontent;
    LinearLayout donationcardfront, donationcardplacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_tracker);

        donategenerate = findViewById(R.id.generateuid_sample); // sample button only
        donationcardplacement = findViewById(R.id.ll_donationstracker_donationcardplacement);
        donationcardfront = findViewById(R.id.layout_donationcard_front);
        donationcardcontent = findViewById(R.id.rl_donationcard_content);
        cardView = findViewById(R.id.cardView);

        LayoutInflater inflater = getLayoutInflater();

        donategenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = inflater.inflate(R.layout.donation_card, null );
                donationcardplacement.addView(view);
            }
        });

        // donation card expands and collapses
        donationcardfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (donationcardcontent.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.VISIBLE);
                } else if (donationcardcontent.getVisibility()!=View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.GONE);
                }
            }
        });

    }

}