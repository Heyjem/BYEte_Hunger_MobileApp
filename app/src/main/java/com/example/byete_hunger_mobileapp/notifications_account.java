package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class notifications_account extends AppCompatActivity {

    ImageView back, account;
    SwitchCompat pushNotif;
    SharedPreferences getPushNotif;
    boolean isPushNotifOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_account);

        back = findViewById(R.id.accountNotifs_back);
        account = findViewById(R.id.accountNotifs_account);
        pushNotif = findViewById(R.id.switch1);

        getPushNotif = PreferenceManager.getDefaultSharedPreferences(this);

        isPushNotifOn = getPushNotif.getBoolean("pushNotif", true);

        pushNotif.setChecked(isPushNotifOn);

        pushNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getPushNotif.edit().putBoolean("pushNotif",true).apply();
                }else{
                    getPushNotif.edit().putBoolean("pushNotif",false).apply();
                }
            }
        });

        // Back to Previous Page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

}