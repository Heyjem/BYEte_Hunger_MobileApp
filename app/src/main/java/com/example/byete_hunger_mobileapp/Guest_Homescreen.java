package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Guest_Homescreen extends AppCompatActivity {

    TextView ClickHere, newsFeed, triviaFeed;
    Button Register;
    FirebaseDatabase fDB;
    DatabaseReference dbRef;
    ImageView announcementImage, newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_homescreen);

        Register =  findViewById(R.id.button_guesthomescreen_register);
        ClickHere = findViewById(R.id.tv_guesthomescreen_clickhere);
        announcementImage = findViewById(R.id.guestannouncementImage);
        newsImage = findViewById(R.id.guest_newsF_Image);
        newsFeed = findViewById(R.id.guest_newsF_Content);
        triviaFeed = findViewById(R.id.guest_Trivia_Content);

        fDB = FirebaseDatabase.getInstance();
        dbRef = fDB.getReference("page-content");

        // redirect to registration main
        ClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Guest_Homescreen.this, FAQs.class));
            }
        });

        // redirect to login page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Guest_Homescreen.this, RegistrationMain.class));
            }
        });

        // insert image in newsfeed
        dbRef.child("newsfeed").child("newsfeedImg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img = (String)snapshot.getValue();
                Glide.with(Guest_Homescreen.this).load(img).override(Target.SIZE_ORIGINAL).into(newsImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert image in announcements
        dbRef.child("announcement").child("announcementImg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img = (String)snapshot.getValue();
                Glide.with(Guest_Homescreen.this).load(img).override(Target.SIZE_ORIGINAL).into(announcementImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert text in news feed
        dbRef.child("newsfeed").child("newsfeedText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String feed = (String)snapshot.getValue();
                newsFeed.setText(feed);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert text in trivia feed
        dbRef.child("trivia").child("triviaText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String trivia = (String)snapshot.getValue();
                triviaFeed.setText(trivia);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}