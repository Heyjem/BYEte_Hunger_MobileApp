package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class statustracker extends AppCompatActivity {

    TextView donationUID;
    ArrayList<donation> list;
    MyAdapter adapter;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;
    ImageView back, account;
    ProgressBar pB;
    Timer timer;
    String dS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statustracker);

        back = findViewById(R.id.donationstracker_back_button);
        account = findViewById(R.id.donationstracker_account_page_icon);
        donationUID = findViewById(R.id.tv_statustracker_uidvalue);
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
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Toast.makeText(statustracker.this, "Donation in for approval", Toast.LENGTH_LONG).show();
                            }
                        }, 1000);
                    }else if(Objects.equals(dS, "Accepted")){
                        pB.setProgress(16);
                    }else if(Objects.equals(dS, "Picked up")){
                        pB.setProgress(42);
                    }else if(Objects.equals(dS, "Dropped off")){
                        pB.setProgress(68);
                    }else if(Objects.equals(dS, "Donated")){
                        pB.setProgress(100);}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // never ignore errors
            }
        });


    }
}
