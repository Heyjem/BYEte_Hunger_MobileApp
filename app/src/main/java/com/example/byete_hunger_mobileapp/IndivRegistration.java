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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IndivRegistration extends AppCompatActivity {

    EditText lastName, firstName, contactNo, location, emailAddress, password;
    TextView PrivacyPolicy, LoginHere;
    Button Register;
    CheckBox checkbox;
    DatabaseReference dbRef;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_registration);

        PrivacyPolicy = findViewById(R.id.textView6_IndivReg_PrivacyPolicy);
        LoginHere = findViewById(R.id.textView8_IndivReg_LoginHere);
        lastName = findViewById(R.id.editText_IndivOrg_LastName);
        firstName = findViewById(R.id.editText_IndivReg_FirstName);
        contactNo = findViewById(R.id.editText_IndivReg_ContactNum);
        location = findViewById(R.id.editText_IndivReg_Location);
        emailAddress = findViewById(R.id.editText_IndivReg_EmailAddress);
        password = findViewById(R.id.editText_IndivReg_Password);
        Register = findViewById(R.id.button3_IndivReg_Register);
        checkbox = findViewById(R.id.checkBox_IndivReg);

        dbRef = FirebaseDatabase.getInstance().getReference();
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
                startActivity(new Intent(IndivRegistration.this, PrivacyPolicy.class));
            }
        });

        // redirects to the login screen
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndivRegistration.this, LoginScreen.class));
            }
        });
    }

    public void Register() {
        String lastNametxt = lastName.getText().toString();
        String firstNametxt = firstName.getText().toString();
        String contactNotxt = contactNo.getText().toString();
        String locationtxt = location.getText().toString();
        String emailAddresstxt = emailAddress.getText().toString().trim();
        String passwordtxt = password.getText().toString().trim();
        String fullName = firstNametxt + " " + lastNametxt;
        String organization = "N/A";
        String contactPerson = "N/A";
        String status = "Pending";

        if(lastNametxt.isEmpty()){
            emailAddress.setError("Please enter your last name.");
        }
        if(firstNametxt.isEmpty()){
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
            fAuth.createUserWithEmailAndPassword(emailAddresstxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentUser = fAuth.getCurrentUser();

                        ReadWriteIndivUserDetails writeUserDetails = new ReadWriteIndivUserDetails(lastNametxt, firstNametxt, fullName, contactNotxt, locationtxt, emailAddresstxt, organization, contactPerson, status);

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");

                        dbRef.child(currentUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Send Verification Email
                                    //currentUser.sendEmailVerification();

                                    //startActivity(new Intent(IndivRegistration.this, Homescreen.class));
                                    Toast.makeText(IndivRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(IndivRegistration.this, LoginScreen.class);

                                    // Prevent user to return to Indiv Registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(IndivRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(IndivRegistration.this, "Registration Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}