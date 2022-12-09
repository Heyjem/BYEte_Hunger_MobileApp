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

import java.util.Objects;

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
                if(!Checkbox.isChecked()){
                    Toast.makeText(OrgRegistration.this, "Please agree to our privacy policy by ticking the checkbox.", Toast.LENGTH_SHORT).show();
                }else if(Checkbox.isChecked()){
                    Register();
                }
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
            organization.setError("Please enter your organization's name.");
            organization.requestFocus();
        }
        else if(contactPersontxt.isEmpty()){
            contactPerson.setError("Please enter your contact person's name");
            contactPerson.requestFocus();
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
            mAuth.createUserWithEmailAndPassword(emailAddresstxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        ReadWriteOrgUserDetails writeUserDetails = new ReadWriteOrgUserDetails(organizationtxt,contactPersontxt,contactNotxt,locationtxt,emailAddresstxt,fullName,status);
                        dbRef = FirebaseDatabase.getInstance().getReference("Users");
                        assert currentUser != null;
                        String uid = currentUser.getUid();

                        dbRef.child(uid).setValue(writeUserDetails).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                Toast.makeText(OrgRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrgRegistration.this, LoginScreen.class);
                                // Prevent user to return to Org Registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(OrgRegistration.this, "Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrgRegistration.this, RegistrationMain.class);
                                startActivity(intent);
                            }
                        });
                    }else{
                        Toast.makeText(OrgRegistration.this, "Error with Registration, Please try again later.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrgRegistration.this, RegistrationMain.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}