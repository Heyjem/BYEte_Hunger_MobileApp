package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class welcome_screen extends AppCompatActivity {

    Button Register;
    TextView Login, ClickHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button Register = (Button)findViewById(R.id.button_welcome_screen);
        TextView Login = (TextView)findViewById(R.id.textView_login_welcome_screen);
        TextView ClickHere = (TextView)findViewById(R.id.textView_clickhere_welcome_screen);

        // redirect to registration main
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(welcome_screen.this,RegistrationMain.class);
                startActivity(intent);

            }
        });

        // redirect to login page
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(welcome_screen.this,LoginScreen.class);
                startActivity(intent);

            }
        });

        // redirect to guest homescreen
        ClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(welcome_screen.this,Guest_Homescreen.class);
                startActivity(intent);

            }
        });

    }
}