package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Homescreen extends AppCompatActivity {

    String uid;
    Button Chat, Track, Donate;
    ImageView account, newsImage, announcementImage;
    TextView faqs, newsFeed, triviaFeed, announceFeed;
    FirebaseDatabase fDB;
    DatabaseReference dbRef, dbRef2;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        onStart();
        onStop();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        account = findViewById(R.id.homescreen_account_page_icon);
        Chat = findViewById(R.id.button_homescreen_chat);
        Track = findViewById(R.id.button_homescreen_track);
        Donate = findViewById(R.id.button_homescreen_donate);
        faqs = findViewById(R.id.tv_homescreen_faqsclickhere);
        announceFeed = findViewById(R.id.announcements_Content);
        newsFeed = findViewById(R.id.newsF_Content);
        newsImage = findViewById(R.id.newsF_Image);
        triviaFeed = findViewById(R.id.Trivia_Content);
        announcementImage = findViewById(R.id.announcementImage);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        fDB = FirebaseDatabase.getInstance();
        dbRef = fDB.getReference("page-content");
        dbRef2 = fDB.getReference("Users");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        uid = currentUser.getUid();


        // insert image in newsfeed
        dbRef.child("newsfeed").child("newsfeedImg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img = (String)snapshot.getValue();
                Glide.with(Homescreen.this).load(img).override(Target.SIZE_ORIGINAL).into(newsImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert image in announcements
        dbRef.child("announcement").child("announcementImg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String img = (String)snapshot.getValue();
                Glide.with(Homescreen.this).load(img).override(Target.SIZE_ORIGINAL).into(announcementImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert text in announcements
        dbRef.child("announcement").child("announcementText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String announce = (String)snapshot.getValue();
                announceFeed.setText(announce);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert text in news feed
        dbRef.child("newsfeed").child("newsfeedText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String feed = (String)snapshot.getValue();
                    newsFeed.setText(feed);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // insert text in trivia feed
        dbRef.child("trivia").child("triviaText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String trivia = (String)snapshot.getValue();
                triviaFeed.setText(trivia);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homescreen.this, Account.class));
            }
        });

        // redirect to chat
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, Chat_Screen.class));
            }
        });

        // redirect to donations tracker
        Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, statustracker.class));
            }
        });

        //donate, only if user is already verified and when recent donation is donated or cancelled
        dbRef2.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String stat = snapshot.child("status").getValue(String.class);
                String unveri = "Donation Unavailable: User Unverified";
                String veri = "DONATE NOW";
                if(Objects.equals(stat, "Pending") || Objects.equals(stat, "Declined")){
                    Donate.setEnabled(false);
                    Donate.setText(unveri);
                    Donate.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }else if(Objects.equals(stat, "Verified")){
                    Donate.setEnabled(true);
                    Donate.setText(veri);
                    Donate.getBackground().setColorFilter(Color.rgb(0,125,70), PorterDuff.Mode.SRC_ATOP);
                    for(DataSnapshot dataSnapshot: snapshot.child("donation").getChildren()){
                        String dS = dataSnapshot.child("donationStatus").getValue(String.class);
                        String disabled = "Donation in process";
                        if(Objects.equals(dS, "Pending") || Objects.equals(dS, "Accepted") || Objects.equals(dS, "Picked-Up") || Objects.equals(dS, "Dropped-Off") ){
                            Donate.setEnabled(false);
                            Donate.setText(disabled);
                            Donate.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        }else if(Objects.equals(dS, "Donated") || Objects.equals(dS, "Cancelled")){
                            Donate.setEnabled(true);
                            Donate.setText(veri);
                            Donate.getBackground().setColorFilter(Color.rgb(0,125,70), PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //chat, only if user is already verified
        dbRef2.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String stat = snapshot.child("status").getValue(String.class);
                String unveri = "CHAT INACCESSIBLE";
                String veri = "CHAT WITH US!";
                if(Objects.equals(stat, "Pending") || Objects.equals(stat, "Declined")){
                    Chat.setEnabled(false);
                    Chat.setText(unveri);
                    Chat.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                }else if(Objects.equals(stat, "Verified")){
                    Chat.setEnabled(true);
                    Chat.setText(veri);
                    Chat.getBackground().setColorFilter(Color.rgb(53,201,60), PorterDuff.Mode.SRC_ATOP);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        String firstFour = uid.substring(0, 4);
        String lastFour = uid.substring(uid.length() - 4);
        String customUID = "FRPH-U-" + firstFour + lastFour;

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",currentUser.getUid());

        HashMap<String,Object> hashMap2 = new HashMap<>();
        hashMap2.put("customUID",customUID);

        //store uid inside current user
        dbRef2.child(currentUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });

        //store custom uid inside current user
        dbRef2.child(currentUser.getUid()).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });


        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, donate.class));
            }
        });

        // redirect to faqs
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this, FAQs.class));
            }
        });
    }


}