package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class uid_donations extends AppCompatActivity {

    ImageView back,account;
    CardView cardview;
    RelativeLayout donationcardcontent;
    LinearLayout donationcardfront;
    RecyclerView recyclerView;
    ArrayList<donation> list;
    MyAdapter adapter;
    DatabaseReference donationsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_donations);

        // toolbar random comment
        back = findViewById(R.id.donationstracker_back_button);
        account = findViewById(R.id.donationstracker_account_page_icon);

        recyclerView = findViewById(R.id.rv_donationstracker);
        donationcardfront = findViewById(R.id.layout_donationcard_front);
        donationcardcontent = findViewById(R.id.rl_donationcard_content);
        donationsDB = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        cardview = findViewById(R.id.cv_donationCard);
        //donategenerate = findViewById(R.id.button5_donate_generateUID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(uid_donations.this,Account.class));
            }
        });


        donationsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    donation Donation = dataSnapshot.getValue(donation.class);
                    list.add(Donation);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // donation card expands and collapses
        /*cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (donationcardcontent.getVisibility()!=View.GONE){
                    TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
                    donationcardcontent.setVisibility(View.GONE);
                } else if (donationcardcontent.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
                    donationcardcontent.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }
}