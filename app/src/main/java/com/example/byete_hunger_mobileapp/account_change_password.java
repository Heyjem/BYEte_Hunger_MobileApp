package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class account_change_password extends AppCompatActivity {


    FirebaseAuth fAuth;
    String email;
    Button send;
    ImageView back, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_change_password);

        fAuth = FirebaseAuth.getInstance();
        send = (Button) this.findViewById(R.id.turnonnotif);
        back = findViewById(R.id.donations_back);
        account = findViewById(R.id.myaccount_account);
        EditText forEmail = (EditText) this.findViewById(R.id.editTextTextPassword1);

        //Change Password
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = forEmail.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(account_change_password.this, "Please provide your e-mail", Toast.LENGTH_SHORT).show();
                }else {
                    changepassword();
                }

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_change_password.this, Account.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void changepassword() {
        fAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(account_change_password.this, "Check your email", Toast.LENGTH_SHORT).show();
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                   finish();
                                }
                            }, 1000);
                        }else {
                            Toast.makeText(account_change_password.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }


}