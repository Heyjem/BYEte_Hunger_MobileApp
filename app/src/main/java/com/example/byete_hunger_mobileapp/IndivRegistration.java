package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

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

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkbox.isChecked()){
                    Toast.makeText(IndivRegistration.this, "Please agree to our privacy policy by ticking the checkbox.", Toast.LENGTH_SHORT).show();
                }else if(checkbox.isChecked()){
                    Register();
                }
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
            lastName.setError("Please enter your last name.");
            lastName.requestFocus();
        }
        else if(firstNametxt.isEmpty()){
            firstName.setError("Please enter your first name");
            firstName.requestFocus();
        }
        else if(contactNotxt.isEmpty()){
            contactNo.setError("Please enter your contact number.");
            contactNo.requestFocus();
        }
        else if(locationtxt.isEmpty()){
            location.setError("Please enter your address.");
            location.requestFocus();
        }
        else if(emailAddresstxt.isEmpty()){
            emailAddress.setError("Please enter your email address.");
            emailAddress.requestFocus();
        }
        else if(passwordtxt.isEmpty() || passwordtxt.matches("^(.{0,7}|[^0-9]*|[^A-Z]*|[a-zA-Z0-9]*)$")){
            password.setError("Password must have more than 8 characters, 1 number, 1 uppercase, & 1 special symbol.");
            password.requestFocus();
        }
        else{
            fAuth.createUserWithEmailAndPassword(emailAddresstxt, passwordtxt).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    ReadWriteIndivUserDetails writeUserDetails = new ReadWriteIndivUserDetails(lastNametxt, firstNametxt, fullName, contactNotxt, locationtxt, emailAddresstxt, organization, contactPerson, status);

                    dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    dbRef.child(currentUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            Toast.makeText(IndivRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(IndivRegistration.this, LoginScreen.class);
                            // Prevent user to return to Indiv Registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(IndivRegistration.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(IndivRegistration.this, "Error with Registration, Please try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}