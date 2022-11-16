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
    SwitchCompat pushNotif, emailNotif;
    SharedPreferences getPushNotif, getEmailNotif;
    boolean isPushNotifOn, isEmailNotifOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_account);

        back = findViewById(R.id.accountNotifs_back);
        account = findViewById(R.id.accountNotifs_account);
        pushNotif = findViewById(R.id.switch1);
        emailNotif = findViewById(R.id.switch2);

        getPushNotif = PreferenceManager.getDefaultSharedPreferences(this);
        getEmailNotif = PreferenceManager.getDefaultSharedPreferences(this);

        isPushNotifOn = getPushNotif.getBoolean("pushNotif", true);
        isEmailNotifOn = getEmailNotif.getBoolean("emailNotif", true);

        pushNotif.setChecked(isPushNotifOn);
        emailNotif.setChecked(isEmailNotifOn);

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

        emailNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getEmailNotif.edit().putBoolean("emailNotif",true).apply();
                }else{
                    getEmailNotif.edit().putBoolean("emailNotif",false).apply();
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