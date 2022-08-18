package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        TextView ForgotPassword = (TextView) this.findViewById(R.id.textView_LoginForgotPassword);
        TextView Register = (TextView) this.findViewById(R.id.textView4_Login_RegisterNow);

        // Redirect to Forgot Password Page
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, forgotPassword.class);
                startActivity(intent);
            }
        });

        // Redirect to Registration Page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegistrationMain.class);
                startActivity(intent);
            }
        });



    }
}