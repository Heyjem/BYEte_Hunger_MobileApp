package com.example.byete_hunger_mobileapp;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        ImageView GoBack = (ImageView) this.findViewById(R.id.imageView14_PrivPol_GoBack);

        TextView textview = (TextView) findViewById(R.id.textView2_PrivPol_Text);
        textview.setMovementMethod(new ScrollingMovementMethod());

        // Back to Previous Page
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}