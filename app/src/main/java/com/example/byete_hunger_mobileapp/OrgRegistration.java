package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrgRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_registration);

        TextView PrivacyPolicy = (TextView) this.findViewById(R.id.textView_OrgRegistration_PrivacyPolicy);
        TextView LoginHere = (TextView) this.findViewById(R.id.textView6_OrgRegistration_LoginHere);

        // redirects to the privacy policy screen
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrgRegistration.this, PrivacyPolicy.class);
                startActivity(intent);
            }

        });

        // redirects to the login screen
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgRegistration.this, LoginScreen.class);
                startActivity(intent);
            }

        });

    }
}