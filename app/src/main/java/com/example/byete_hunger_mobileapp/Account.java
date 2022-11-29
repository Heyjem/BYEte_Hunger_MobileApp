package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class Account extends AppCompatActivity implements View.OnClickListener{

    Button logout;
    TextView fullname, orgname;
    ImageView notifArrow, donationsArrow, changepassArrow, back;
    AppCompatButton notif, donations, changepass;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;
    FirebaseStorage storage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        notifArrow = findViewById(R.id.iv_notifs);
        notifArrow.setOnClickListener(this); //calling onClick method
        donationsArrow = findViewById(R.id.iv_donations);
        donationsArrow.setOnClickListener(this);
        changepassArrow = findViewById(R.id.iv_changepass);
        changepassArrow.setOnClickListener(this);

        notif = findViewById(R.id.turnonnotif);
        notif.setOnClickListener(this);
        donations = findViewById(R.id.DonationsMyAccount);
        donations.setOnClickListener(this);
        changepass = findViewById(R.id.ChangePassMyAccount);
        changepass.setOnClickListener(this);

        back = findViewById(R.id.donations_back);
        logout = findViewById(R.id.myaccount_logout_button);
        fullname = findViewById(R.id.textView2);
        orgname = findViewById(R.id.textView3);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        dbRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cP = dataSnapshot.child("contactPerson").getValue(String.class);
                String fN = dataSnapshot.child("fullName").getValue(String.class);
                String org = dataSnapshot.child("organization").getValue(String.class);
                if(Objects.equals(cP, "N/A")){
                    fullname.setText(fN);
                    orgname.setVisibility(View.INVISIBLE);
                }else if(Objects.equals(fN, "N/A")){
                    fullname.setText(cP);
                    orgname.setText(org);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this, LoginScreen.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_notifs || v.getId() == R.id.turnonnotif) {
            startActivity(new Intent(Account.this, notifications_account.class));
        } else if (v.getId() == R.id.iv_donations || v.getId() == R.id.DonationsMyAccount) {
            startActivity(new Intent(Account.this, uid_donations.class));
        } else if (v.getId() == R.id.iv_changepass || v.getId() == R.id.ChangePassMyAccount) {
            startActivity(new Intent(Account.this, account_change_password.class));
        }
    }
}