package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class statustracker extends AppCompatActivity {

    TextView donationUID;
    ArrayList<donation> list;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;
    ImageView back, account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statustracker);

        back = findViewById(R.id.donationstracker_back_button);
        account = findViewById(R.id.donationstracker_account_page_icon);
        donationUID = findViewById(R.id.tv_statustracker_uidvalue);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

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




    }
}