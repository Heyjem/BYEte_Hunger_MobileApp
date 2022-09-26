package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {

    Button logout;
    ImageView notif, donate, changepass;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        notif = findViewById(R.id.iv_notifs);
        donate = findViewById(R.id.iv_donations);
        changepass = findViewById(R.id.iv_changepass);
        logout = findViewById(R.id.myaccount_logout_button);
        fAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this, LoginScreen.class));
            }
        });

        // redirect to account notifications
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Account.this, notifications_account.class));
            }
        });

        // redirect to account donations
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Account.this, donations_tracker.class));
            }
        });

        // redirect to account change password
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Account.this, account_change_password.class));
            }
        });


    }
}