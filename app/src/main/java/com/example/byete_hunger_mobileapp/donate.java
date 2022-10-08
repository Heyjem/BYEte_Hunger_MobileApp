package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit, UID;
    ImageView back, account;
    EditText weight, datePurchased, dateExpired, contactNo, notes;
    DatabaseReference dbRef;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        back = findViewById(R.id.donate_back_button);
        account = findViewById(R.id.donate_account_page_icon);
        recyclerView = findViewById(R.id.rv_donationstracker);
        spinner = findViewById(R.id.spinner_donate);
        weight = findViewById(R.id.et_donate_weight);
        datePurchased = findViewById(R.id.et_donate_donatePurchased);
        dateExpired = findViewById(R.id.et_donate_donateExpired);
        contactNo = findViewById(R.id.et_donate_contactNo);
        notes = findViewById(R.id.et_donate_notes);
        Submit = findViewById(R.id.button4_donate_submit);
        UID = findViewById(R.id.button5_donate_generateUID);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(donate.this, Account.class));
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        UID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(donate.this, "pindot pa", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void InsertData() {
        String type = spinner.getSelectedItem().toString();
        String wt = weight.getText().toString();
        String dP = datePurchased.getText().toString();
        String dE = dateExpired.getText().toString();
        String cN = contactNo.getText().toString();
        String nts = notes.getText().toString();
        String id = dbRef.push().getKey();

        //show when new card was added to donations
        Date date = new Date();
        Date time = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
        String dateAdded = formatter.format(date);
        String dateAddedTime = formatter2.format(time);


        donation Donation = new donation(type, wt, dP, dE, cN, nts, id, dateAdded, dateAddedTime);
        dbRef.child("Unverified Registered User").child(currentUser.getUid()).child("donation").child(id).setValue(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(donate.this,"Donation details inserted", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
//"Unverified Registered Users").child(currentUser.getUid()