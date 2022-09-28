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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit, UID;
    ImageView back, account;
    EditText weight, datePurchased, dateExpired, contactNo, notes;
    DatabaseReference donationsDB;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        back = findViewById(R.id.donate_back_button);
        account = findViewById(R.id.donate_account_page_icon);
        recyclerView = findViewById(R.id.rv_donationstracker);
        spinner = (Spinner)findViewById(R.id.spinner_donate);
        weight = (EditText)findViewById(R.id.et_donate_weight);
        datePurchased = (EditText)findViewById(R.id.et_donate_donatePurchased);
        dateExpired = (EditText)findViewById(R.id.et_donate_donateExpired);
        contactNo = (EditText)findViewById(R.id.et_donate_contactNo);
        notes = (EditText)findViewById(R.id.et_donate_notes);
        Submit = (Button)findViewById(R.id.button4_donate_submit);
        UID = (Button)findViewById(R.id.button5_donate_generateUID);
        donationsDB = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

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

            public int nDigitRandomNo(int digits){
                int max = (int) Math.pow(10,(digits)) - 1; //for digits =7, max will be 9999999
                int min = (int) Math.pow(10, digits-1); //for digits = 7, min will be 1000000
                int range = max-min; //This is 8999999
                Random r = new Random();
                int x = r.nextInt(range);// This will generate random integers in range 0 - 8999999
                int nDigitRandomNo = x+min; //Our random rumber will be any random number x + min
                return nDigitRandomNo;
            }

            @Override
            public void onClick(View v) {
                InsertData();

                /*
                CardView newCard = new CardView(donate.this);
                getLayoutInflater().inflate(R.layout.donation_card, newCard);

                int digits = 7;
                int n = nDigitRandomNo(digits);
                String uid = String.valueOf(n);

                TextView t = newCard.findViewById(R.id.tv_donationcard_uidCode);
                t.setText(uid);
                newCard.setTag(uid);

                recyclerView.addView(newCard);*/
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
        String id = donationsDB.push().getKey();

        donation Donation = new donation(type, wt, dP, dE, cN, nts);
        donationsDB.child("donation").child(id).setValue(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(donate.this,"Donation details inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}