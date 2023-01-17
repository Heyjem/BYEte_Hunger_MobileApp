package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;
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

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Prevent CheckBox state from being toggled when link is clicked
                widget.cancelPendingInputEvents();
                startActivity(new Intent(IndivRegistration.this, PrivacyPolicy.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        String privpol = "By ticking, you are confirming that you have read, understood and agree to our Privacy Policy.";
        SpannableString linkText = new SpannableString(privpol);
        linkText.setSpan(clickableSpan, 79,93, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkbox.setText(linkText);// Finally, make links clickable
        checkbox.setMovementMethod(LinkMovementMethod.getInstance());

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
            fAuth.createUserWithEmailAndPassword(emailAddresstxt, passwordtxt).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Map map = new HashMap();
                        map.put("timestamp", ServerValue.TIMESTAMP);

                        ReadWriteIndivUserDetails writeUserDetails = new ReadWriteIndivUserDetails(lastNametxt, firstNametxt, fullName, contactNotxt, locationtxt, emailAddresstxt, organization, contactPerson, status, ServerValue.TIMESTAMP);
                        dbRef.child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(IndivRegistration.this, "Registration successful, client verification underway", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(IndivRegistration.this, LoginScreen.class);
                                    // Prevent user to return to Indiv Registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(IndivRegistration.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(IndivRegistration.this, RegistrationMain.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(IndivRegistration.this, "Error with Registration, Please try again later.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(IndivRegistration.this, RegistrationMain.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}