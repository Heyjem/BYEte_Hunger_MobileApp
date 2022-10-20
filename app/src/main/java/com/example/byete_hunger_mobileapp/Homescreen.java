package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Homescreen extends AppCompatActivity {

    Button Chat, Track, Donate;
    ImageView account, newsImage;
    TextView faqs, newsFeed, triviaFeed;
    FirebaseDatabase fDB;
    DatabaseReference dbRef, dbRef2;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        onStart();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();


        account = findViewById(R.id.homescreen_account_page_icon);
        Chat = findViewById(R.id.button_homescreen_chat);
        Track = findViewById(R.id.button_homescreen_track);
        Donate = findViewById(R.id.button_homescreen_donate);
        faqs = findViewById(R.id.tv_homescreen_faqsclickhere);
        newsFeed = findViewById(R.id.newsF_Content);
        newsImage = findViewById(R.id.newsF_Image);
        triviaFeed = findViewById(R.id.Trivia_Content);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        fDB = FirebaseDatabase.getInstance();
        dbRef = fDB.getReference("page-content");
        dbRef2 = fDB.getReference("Users");

        String uid = currentUser.getUid();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",uid);

        dbRef2.child(uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });


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

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homescreen.this, Account.class));
            }
        });

        // redirect to chat
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, Chat_Screen.class));
            }
        });

        // redirect to donations tracker
        Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, statustracker.class));
            }
        });

        // redirect to donate
        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, donate.class));
            }
        });

        // redirect to faqs
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, FAQs.class));
            }
        });
    }

    public void onStart(){
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        if (currentUser == null){
            startActivity(new Intent(Homescreen.this, LoginScreen.class));
        }
    }

}