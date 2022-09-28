package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class statustracker extends AppCompatActivity {

    TextView donationUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statustracker);

        donationUID = findViewById(R.id.tv_statustracker_uidvalue);

        donationUID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(statustracker.this, uid_donations.class));
            }
        });
    }
}