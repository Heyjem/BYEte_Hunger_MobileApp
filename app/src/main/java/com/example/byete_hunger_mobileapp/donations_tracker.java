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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class donations_tracker extends AppCompatActivity {

    Button donategenerate, buttonDoSth;
    CardView cardView;
    RelativeLayout donationcardcontent;
    LinearLayout donationcardfront, donationcardplacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_tracker);

        cardView = findViewById(R.id.cardView);
        donategenerate = findViewById(R.id.generateuid_sample); // sample button only
        donationcardplacement = findViewById(R.id.ll_donationstracker_donationcardplacement);
        donationcardfront = findViewById(R.id.layout_donationcard_front);
        donationcardcontent = findViewById(R.id.rl_donationcard_content);

        LayoutInflater inflater = getLayoutInflater();

        donategenerate.setOnClickListener(new View.OnClickListener() {

            public int nDigitRandomNo(int digits){
                int max = (int) Math.pow(10,(digits)) - 1; //for digits =7, max will be 9999999
                int min = (int) Math.pow(10, digits-1); //for digits = 7, min will be 1000000
                int range = max-min; //This is 8999999
                Random r = new Random();
                int x = r.nextInt(range);// This will generate random integers in range 0 - 8999999
                int nDigitRandomNo = x+min; //Our random rumber will be any random number x + min
                return nDigitRandomNo;
            }

            @Override
            public void onClick(View v) {
                CardView newCard = new CardView(donations_tracker.this);
                getLayoutInflater().inflate(R.layout.donation_card, newCard);

                int digits = 7;
                int n = nDigitRandomNo(digits);
                String uid = String.valueOf(n);

                TextView t = newCard.findViewById(R.id.tv_donationcard_uidCode);
                t.setText(uid);
                newCard.setTag(uid);

                donationcardplacement.addView(newCard);
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