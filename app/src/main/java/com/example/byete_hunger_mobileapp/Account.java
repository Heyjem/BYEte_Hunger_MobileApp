package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Account extends AppCompatActivity {

    Button notif;
    Button donate;
    Button changepass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button notif = (Button)findViewById(R.id.turnonnotif);
        Button donate = (Button)findViewById(R.id.DonationsMyAccount);
        Button changepass = (Button)findViewById(R.id.ChangePassMyAccount);

        // redirect to account notifications
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, notifications_account.class);
                startActivity(intent);
            }
        });

        // redirect to account donations
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, donations_tracker.class);
                startActivity(intent);
            }
        });

        // redirect to account change password
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, account_change_password.class);
                startActivity(intent);
            }
        });


    }
}