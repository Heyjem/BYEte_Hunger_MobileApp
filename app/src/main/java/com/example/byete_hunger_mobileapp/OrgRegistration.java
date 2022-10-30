package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class OrgRegistration extends AppCompatActivity {

    TextView PrivacyPolicy, LoginHere;
    EditText organization, contactPerson, contactNo, location, emailAddress, password;
    CheckBox Checkbox;
    Button Register;

    DatabaseReference dbRef;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_registration);

        PrivacyPolicy = findViewById(R.id.textView_OrgRegistration_PrivacyPolicy);
        LoginHere = findViewById(R.id.textView6_OrgRegistration_LoginHere);
        organization = findViewById(R.id.editText_OrgRegistration_Organization);
        contactPerson = findViewById(R.id.editText_OrgRegistration_ContactPerson);
        contactNo = findViewById(R.id.editText_OrgRegistration_Contact);
        location = findViewById(R.id.editText_OrgRegistration_Location);
        emailAddress = findViewById(R.id.editText_OrgRegistration_EmailAdd);
        password = findViewById(R.id.editText_OrgRegistration_Password);
        Checkbox = findViewById(R.id.checkBox_OrgReg);
        Register = findViewById(R.id.button3_OrgRegistration_Register);

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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
        String organizationtxt = organization.getText().toString();
        String contactPersontxt = contactPerson.getText().toString();
        String contactNotxt = contactNo.getText().toString();
        String locationtxt = location.getText().toString();
        String emailAddresstxt = emailAddress.getText().toString().trim();
        String passwordtxt = password.getText().toString().trim();
        String fullName = "N/A";
        String status = "Pending";

        
        if(organizationtxt.isEmpty()){
            emailAddress.setError("Please enter your last name.");
        }
        if(contactPersontxt.isEmpty()){
            password.setError("Please enter your first name");
        }
        if(contactNotxt.isEmpty()){
            emailAddress.setError("Please enter your contact number.");
        }
        if(locationtxt.isEmpty()){
            password.setError("Please enter your location");
        }
        if(emailAddresstxt.isEmpty()){
            emailAddress.setError("Please enter your email address.");
        }
        if(passwordtxt.isEmpty() || passwordtxt.length() < 8){
            password.setError("Please enter your password with more than 8 characters.");
        }else{
            mAuth.createUserWithEmailAndPassword(emailAddresstxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        ReadWriteOrgUserDetails writeUserDetails = new ReadWriteOrgUserDetails(organizationtxt,contactPersontxt,contactNotxt,locationtxt,emailAddresstxt,fullName,status);
                        dbRef = FirebaseDatabase.getInstance().getReference("Users");

                        dbRef.child(currentUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //startActivity(new Intent(OrgRegistration.this, Homescreen.class));

                                    Toast.makeText(OrgRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OrgRegistration.this, LoginScreen.class);

                                    // Prevent user to return to Indiv Registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(OrgRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(OrgRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}