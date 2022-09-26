package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class OrgRegistration extends AppCompatActivity {

    TextView PrivacyPolicy, LoginHere;
    EditText Organization, ContactPerson, ContactNo, Location, EmailAddress, Password;
    CheckBox Checkbox;
    Button Register;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_registration);

        PrivacyPolicy = findViewById(R.id.textView_OrgRegistration_PrivacyPolicy);
        LoginHere = findViewById(R.id.textView6_OrgRegistration_LoginHere);
        Organization = findViewById(R.id.editText_OrgRegistration_Organization);
        ContactPerson = findViewById(R.id.editText_OrgRegistration_ContactPerson);
        ContactNo = findViewById(R.id.editText_OrgRegistration_Contact);
        Location = findViewById(R.id.editText_OrgRegistration_Location);
        EmailAddress = findViewById(R.id.editText_OrgRegistration_EmailAdd);
        Password = findViewById(R.id.editText_OrgRegistration_Password);
        Checkbox = findViewById(R.id.checkBox_OrgReg);
        Register = findViewById(R.id.button3_OrgRegistration_Register);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

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
                startActivity(new Intent(OrgRegistration.this, PrivacyPolicy.class));
            }

        });

        // redirects to the login screen
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrgRegistration.this, LoginScreen.class));
            }

        });

    }

    public void Register() {
        String Organizationtxt = Organization.getText().toString();
        String ContactPersontxt = ContactPerson.getText().toString();
        String ContactNotxt = ContactNo.getText().toString();
        String Locationtxt = Location.getText().toString();
        String EmailAddresstxt = EmailAddress.getText().toString();
        String Passwordtxt = Password.getText().toString();

        if(Organizationtxt.isEmpty()){
            EmailAddress.setError("Please enter your last name.");
        }
        if(ContactPersontxt.isEmpty()){
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
            fAuth.createUserWithEmailAndPassword(EmailAddresstxt, Passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(OrgRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrgRegistration.this, Homescreen.class));
                    }else{
                        Toast.makeText(OrgRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}