package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class notifications_account extends AppCompatActivity {

    ImageView back, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_account);

        back = findViewById(R.id.accountNotifs_back);
        back = findViewById(R.id.accountNotifs_account);

        // Back to Previous Page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}