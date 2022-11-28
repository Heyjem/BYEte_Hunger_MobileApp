package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class splashscreen extends AppCompatActivity {

    Handler handler;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPref = getSharedPreferences("Preference", 0);
        String firstTime = sharedPref.getString("firstTimeRun", "Yes");

        if(firstTime.equals("Yes")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("firstTimeRun", "No");
            editor.apply();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),welcome_screen.class));
                }
            },1000);

        }else{
            startActivity(new Intent(getApplicationContext(),LoginScreen.class));
        }





    }
}