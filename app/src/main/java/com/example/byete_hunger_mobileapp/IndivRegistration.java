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
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_registration);

        PrivacyPolicy = (TextView)findViewById(R.id.textView6_IndivReg_PrivacyPolicy);
        LoginHere = (TextView)findViewById(R.id.textView8_IndivReg_LoginHere);
        LastName = (EditText)findViewById(R.id.editText_IndivOrg_LastName);
        FirstName = (EditText)findViewById(R.id.editText_IndivReg_FirstName);
        ContactNo = (EditText)findViewById(R.id.editText_IndivReg_ContactNum);
        Location = (EditText)findViewById(R.id.editText_IndivReg_Location);
        EmailAddress = (EditText)findViewById(R.id.editText_IndivReg_EmailAddress);
        Password = (EditText)findViewById(R.id.editText_IndivReg_Password);
        Register = (Button)findViewById(R.id.button3_IndivReg_Register);
        checkbox = (CheckBox)findViewById(R.id.checkBox_IndivReg);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
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

    public void Register() {
        String LastNametxt = LastName.getText().toString();
        String FirstNametxt = FirstName.getText().toString();
        String ContactNotxt = ContactNo.getText().toString();
        String Locationtxt = Location.getText().toString();
        String EmailAddresstxt = EmailAddress.getText().toString().trim();
        String Passwordtxt = Password.getText().toString().trim();

        if(LastNametxt.isEmpty()){
            EmailAddress.setError("Please enter your last name.");
        }
        if(FirstNametxt.isEmpty()){
            Password.setError("Please enter your first name");
        }
        if(ContactNotxt.isEmpty()){
            EmailAddress.setError("Please enter your contact number.");
        }
        if(Locationtxt.isEmpty()){
            Password.setError("Please enter your location");
        }
        if(EmailAddresstxt.isEmpty()){
            EmailAddress.setError("Please enter your email address.");
        }
        if(Passwordtxt.isEmpty() || Passwordtxt.length() < 8){
            Password.setError("Please enter your password with more than 8 characters.");
        }else{
            mAuth.signInWithEmailAndPassword(EmailAddresstxt, Passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(IndivRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(IndivRegistration.this, Homescreen.class));
                    }else{
                        Toast.makeText(IndivRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}