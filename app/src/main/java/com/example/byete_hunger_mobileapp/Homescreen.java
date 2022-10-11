package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Homescreen extends AppCompatActivity {

    Button Chat, Track, Donate;
    ImageView account;
    TextView faqs;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        account = findViewById(R.id.homescreen_account_page_icon);
        Chat = findViewById(R.id.button_homescreen_chat);
        Track = findViewById(R.id.button_homescreen_track);
        Donate = findViewById(R.id.button_homescreen_donate);
        faqs = findViewById(R.id.tv_homescreen_faqsclickhere);

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