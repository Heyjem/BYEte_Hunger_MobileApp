package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Chat_Screen extends AppCompatActivity {

    ImageView back, account;
    ProgressBar prog;
    FrameLayout send;
    EditText inputM;

    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;

    RecyclerView recyclerView;
    ArrayList<Chat> list;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        // toolbar
        back = findViewById(R.id.chat_back_button);
        account = findViewById(R.id.chat_account_page_icon);
        prog = findViewById(R.id.progressBar);
        send = findViewById(R.id.sendButton);
        recyclerView = findViewById(R.id.chatRecyclerView);
        inputM = findViewById(R.id.inputMessage);

        // Firebase
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<Chat>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(this, list);
        recyclerView.setAdapter(adapter);

        dbRef.child("Chats-Test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prog.setVisibility(View.INVISIBLE);
                if(dataSnapshot.child(currentUser.getUid()).exists()) {

                } else {
                    dbRef.child("Chats-Test").child(currentUser.getUid()).setValue("");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getCode() + ": " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String id = dbRef.push().getKey();
        dbRef.child("Chats-Test").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    list.add(0,chat);
                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputM.getText().toString();
                DatabaseReference chatsRef = dbRef.child("Chats-Test").child(currentUser.getUid());

                DatabaseReference newChatRef = chatsRef.push();
                newChatRef.setValue(new Chat(message, 0));
                inputM.setText("");

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chat_Screen.this,Account.class));
            }
        });


    }
}