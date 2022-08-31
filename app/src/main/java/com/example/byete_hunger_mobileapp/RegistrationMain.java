package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegistrationMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main);
        spinner = findViewById(R.id.spinner_RegistrationDropdown);
        Continue = (Button)findViewById(R.id.button3_Registration_Continue);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Choose Org or Individual Registration
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();

                if (text.equals("Organization")) {
                    Intent intent = new Intent(RegistrationMain.this, OrgRegistration.class);
                    startActivity(intent);
                }else if(text.equals("Individual")){
                    Intent intent=new Intent(v.getContext(),IndivRegistration.class);
                    startActivity(intent);
                }
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getBaseContext(), choice, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}