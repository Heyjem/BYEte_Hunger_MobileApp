package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;

public class statustracker extends AppCompatActivity {

    TextView donationUID,donationAccepted, donationPicked, donationDropped, donationComplete;
    ArrayList<donation> list;
    MyAdapter adapter;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;
    ImageView back, account;
    ProgressBar pB;
    String dS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statustracker);

        back = findViewById(R.id.donationstracker_back_button);
        account = findViewById(R.id.donationstracker_account_page_icon);
        donationUID = findViewById(R.id.tv_statustracker_uidvalue);

        donationAccepted = findViewById(R.id.tv_statustracker_donationconfirmed);
        donationPicked = findViewById(R.id.tv_statustracker_donationpicked);
        donationDropped = findViewById(R.id.tv_statustracker_donationdropped);
        donationComplete = findViewById(R.id.tv_statustracker_donationdonated);


        pB = findViewById(R.id.pB_statTracker);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(statustracker.this, Account.class));
            }
        });

        pB.setMax(100);
        dbRef.child(currentUser.getUid()).child("donation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    // get uid of recent donation
                    String id = dataSnapshot.child("id").getValue(String.class);
                    donationUID.setText(id);
                }
                for (DataSnapshot dataSnapshot2: snapshot.getChildren()){
                    // get donation status of recent donation
                    dS = dataSnapshot2.child("donationStatus").getValue(String.class);
                    if(Objects.equals(dS, "Pending")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Pending");
                        pB.setProgress(0);
                    }else if(Objects.equals(dS, "Accepted")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(16);
                    }else if(Objects.equals(dS, "Picked up")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(42);
                    }else if(Objects.equals(dS, "Dropped off")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationDropped.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(68);
                    }else if(Objects.equals(dS, "Donated")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationDropped.setVisibility(View.VISIBLE);
                        donationComplete.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(100);
                    }else if(Objects.equals(dS, "Cancelled")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.INVISIBLE);
                        donationDropped.setVisibility(View.INVISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Cancelled");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        pB.setProgress(100);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // never ignore errors
            }
        });
    }


    public String date(String dateAdded){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        dateAdded = formatter.format(date);

        return dateAdded;
    }

    public String time(String dateAddedTime){
        Date time = new Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss a");
        dateAddedTime = formatter2.format(time);

        return dateAddedTime;
    }


}
