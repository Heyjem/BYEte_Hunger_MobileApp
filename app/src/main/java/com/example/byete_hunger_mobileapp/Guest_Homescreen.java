package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Guest_Homescreen extends AppCompatActivity {

    Button ClickHere;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_homescreen);

        Button ClickHere = (Button)findViewById(R.id.button_guesthomescreen_register);
        TextView Register = (TextView)findViewById(R.id.tv_guesthomescreen_clickhere);

        // redirect to registration main
        ClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guest_Homescreen.this, FAQs.class);
                startActivity(intent);
            }
        });

        // redirect to login page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guest_Homescreen.this, RegistrationMain.class);
                startActivity(intent);
            }
        });

    }
}