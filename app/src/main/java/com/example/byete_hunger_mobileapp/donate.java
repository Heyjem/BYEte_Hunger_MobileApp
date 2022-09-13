package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit, UID;
    EditText weight, datePurchased, dateExpired, contactNo, notes;
    DatabaseReference donations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        spinner = findViewById(R.id.spinner_donate);
        weight = (EditText)findViewById(R.id.et_donate_weight);
        datePurchased = (EditText)findViewById(R.id.et_donate_donatePurchased);
        dateExpired = (EditText)findViewById(R.id.et_donate_donateExpired);
        contactNo = (EditText)findViewById(R.id.et_donate_contactNo);
        notes = (EditText)findViewById(R.id.et_donate_notes);
        Submit = (Button)findViewById(R.id.button4_donate_submit);
        UID = (Button)findViewById(R.id.button5_donate_generateUID);
        donations = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(donate.this, "pindot pa", Toast.LENGTH_SHORT).show();
            }
        });

        UID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

    }

    private void InsertData() {
        String type = spinner.getSelectedItem().toString();
        String wt = weight.getText().toString();
        String dP = datePurchased.getText().toString();
        String dE = dateExpired.getText().toString();
        String cN = contactNo.getText().toString();
        String nts = notes.getText().toString();
        String id = donations.push().getKey();

        donation donation = new donation(type, wt, dP, dE, cN, nts);
        donations.child("donation").child(id).setValue(donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(donate.this,"Donation details inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
}