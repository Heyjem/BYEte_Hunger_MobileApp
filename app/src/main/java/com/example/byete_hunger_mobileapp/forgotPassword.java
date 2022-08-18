package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class forgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ImageView GoBack = (ImageView) this.findViewById(R.id.imageView5_ForgotPass_BackImage);
        TextView BackToLogin = (TextView) this.findViewById(R.id.textView2_ForgotPass_BackToLogin);

        // Go Back to Login
        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotPassword.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        // Back to Previous Page
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}