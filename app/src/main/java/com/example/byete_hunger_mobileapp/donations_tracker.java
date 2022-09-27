package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class donations_tracker extends AppCompatActivity {

    Button donategenerate;
    CardView cardView;
    RelativeLayout donationcardcontent;
    LinearLayout donationcardfront, donationcardplacement;
    RecyclerView recyclerView;
    ArrayList<donation> list;
    MyAdapter adapter;
    DatabaseReference donationsDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_tracker);

        recyclerView = findViewById(R.id.rv_donationstracker);
        donationcardfront = findViewById(R.id.layout_donationcard_front);
        donationcardcontent = findViewById(R.id.rl_donationcard_content);
        //donationcardplacement = findViewById(R.id.ll_donationcardplacement);
        donationsDB = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        cardView = findViewById(R.id.cardView);
        donategenerate = findViewById(R.id.generateuid_sample); // sample button only


        donationsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    donation Donation = dataSnapshot.getValue(donation.class);
                    list.add(Donation);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        donategenerate.setOnClickListener(new View.OnClickListener() {
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
                CardView newCard = new CardView(donations_tracker.this);
                getLayoutInflater().inflate(R.layout.donation_card, newCard);

                int digits = 7;
                int n = nDigitRandomNo(digits);
                String uid = String.valueOf(n);

                TextView t = newCard.findViewById(R.id.tv_donationcard_uidCode);
                t.setText(uid);
                newCard.setTag(uid);

                recyclerView.addView(newCard);
            }
        });


        // donation card expands and collapses
        /*cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (donationcardcontent.getVisibility()!=View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.GONE);
                } else if (donationcardcontent.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    donationcardcontent.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }

}