package com.example.byete_hunger_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        spinner = findViewById(R.id.spinner_donate);
        Submit = (Button)findViewById(R.id.button4_donate_submit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

        // Choose Org or Individual Registration
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();

              //  if (text.equals("Organization")) {
              //      Intent intent = new Intent(RegistrationMain.this, OrgRegistration.class);
              //      startActivity(intent);
              //  }else if(text.equals("Individual")){
              //      Intent intent=new Intent(v.getContext(),IndivRegistration.class);
              //      startActivity(intent);
              //  }
            }
        });

    }
}