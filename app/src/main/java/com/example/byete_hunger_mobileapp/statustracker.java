package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

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
    FirebaseMessaging fMess;

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

        fMess = FirebaseMessaging.getInstance();

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
                    String id = dataSnapshot.child("customDonationUID").getValue(String.class);
                    donationUID.setText(id);
                }
                for (DataSnapshot dataSnapshot2: snapshot.getChildren()){
                    // get donation status of recent donation
                    dS = dataSnapshot2.child("donationStatus").getValue(String.class);
                    if(Objects.equals(dS, "Pending")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.INVISIBLE);
                        donationDropped.setVisibility(View.INVISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Pending");
                        pB.setProgress(0);
                    }else if(Objects.equals(dS, "Accepted")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.INVISIBLE);
                        donationDropped.setVisibility(View.INVISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(22);
                    }else if(Objects.equals(dS, "Picked-Up")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationDropped.setVisibility(View.INVISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(45);
                    }else if(Objects.equals(dS, "Dropped-Off")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationDropped.setVisibility(View.VISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(70);
                    }else if(Objects.equals(dS, "Donated")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.VISIBLE);
                        donationDropped.setVisibility(View.VISIBLE);
                        donationComplete.setVisibility(View.VISIBLE);
                        donationAccepted.setText("Donation Accepted");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.rgb(46,182,116))); //dark green color
                        pB.setProgress(100);
                    }else if(Objects.equals(dS, "Declined")){
                        donationAccepted.setVisibility(View.VISIBLE);
                        donationPicked.setVisibility(View.INVISIBLE);
                        donationDropped.setVisibility(View.INVISIBLE);
                        donationComplete.setVisibility(View.INVISIBLE);
                        donationAccepted.setText("Donation Cancelled");
                        pB.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        pB.setProgress(100);
                    }
                }
                if(donationComplete.getVisibility() == View.VISIBLE && Objects.equals(dS, "Donated") && getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(statustracker.this,acknowledgement_screen.class);
                            startActivity(intent);
                        }
                    }, 1000); // wait for 1 second
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // never ignore errors
            }
        });
    }

}
