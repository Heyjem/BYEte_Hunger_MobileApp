package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homescreen extends AppCompatActivity {

    Button Chat, Track, Donate;
    TextView faqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        Button Chat = (Button)findViewById(R.id.button_homescreen_chat);
        Button Track = (Button)findViewById(R.id.button_homescreen_track);
        Button Donate = (Button)findViewById(R.id.button_homescreen_donate);
        TextView faqs = (TextView)findViewById(R.id.tv_homescreen_faqsclickhere);


        // redirect to chat
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homescreen.this, RegistrationMain.class);
                startActivity(intent);
            }
        });

        // redirect to donations tracker
        Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homescreen.this, donations_tracker.class);
                startActivity(intent);
            }
        });

        // redirect to guest donate
        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homescreen.this, donate.class);
                startActivity(intent);
            }
        });

        // redirect to faqs
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homescreen.this, FAQs.class);
                startActivity(intent);
            }
        });


    }
}