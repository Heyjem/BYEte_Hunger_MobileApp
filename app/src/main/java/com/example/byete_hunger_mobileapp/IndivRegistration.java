package com.example.byete_hunger_mobileapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndivRegistration extends AppCompatActivity {

    EditText LastName, FirstName, ContactNo, Location, EmailAddress, Password;
    TextView PrivacyPolicy, LoginHere;
    Button Register;
    CheckBox checkbox;
    String emailPattern1 = "[a-zA-Z9._-]+@[a-z]+\\.+[a-z]+", emailPattern2 = "[a-zA-Z9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_registration);

        PrivacyPolicy = (TextView) findViewById(R.id.textView6_IndivReg_PrivacyPolicy);
        LoginHere = (TextView) findViewById(R.id.textView8_IndivReg_LoginHere);
        LastName = (EditText) findViewById(R.id.editText_IndivOrg_LastName);
        FirstName = (EditText) findViewById(R.id.editText_IndivReg_FirstName);
        ContactNo = (EditText) findViewById(R.id.editText_IndivReg_ContactNum);
        Location = (EditText) findViewById(R.id.editText_IndivReg_Location);
        EmailAddress = (EditText) findViewById(R.id.editText_IndivReg_EmailAddress);
        Password = (EditText) findViewById(R.id.editText_IndivReg_Password);
        Register = (Button) findViewById(R.id.button3_IndivReg_Register);
        checkbox = (CheckBox) findViewById(R.id.checkBox_IndivReg);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });

        // redirects to the privacy policy screen
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(IndivRegistration.this, PrivacyPolicy.class);
                startActivity(intent);
            }

        });

        // redirects to the login screen
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(IndivRegistration.this, LoginScreen.class);
                startActivity(intent);
            }

        });

    }

    public void PerforAuth() {
        String LastNametxt = LastName.getText().toString();
        String FirstNametxt = FirstName.getText().toString();
        String ContactNotxt = ContactNo.getText().toString();
        String Locationtxt = Location.getText().toString();
        String EmailAddresstxt = EmailAddress.getText().toString();
        String Passwordtxt = Password.getText().toString();

        if (LastNametxt.isEmpty() || FirstNametxt.isEmpty() || ContactNotxt.isEmpty() || Locationtxt.isEmpty() || EmailAddresstxt.isEmpty() || Passwordtxt.isEmpty()){
            Toast.makeText(IndivRegistration.this, "Please enter your complete account details.", Toast.LENGTH_LONG).show();
        }else if(Passwordtxt.isEmpty() || Passwordtxt.length() < 8){
            Password.setError("Incomplete Password Credentials");
        }else if(!checkbox.isChecked()){
            Toast.makeText(IndivRegistration.this, "Please accept and tick our privacy policy", Toast.LENGTH_LONG).show();
        }else if(EmailAddresstxt.matches(emailPattern1) || EmailAddresstxt.matches(emailPattern2)){
            fAuth.createUserWithEmailAndPassword(EmailAddresstxt,Passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(IndivRegistration.this, "Registration successful, client verification is underway", Toast.LENGTH_LONG).show();
                        intent = new Intent(IndivRegistration.this, LoginScreen.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(IndivRegistration.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            EmailAddress.setError("Incorrect Email Credentials.");

        }

    }

}