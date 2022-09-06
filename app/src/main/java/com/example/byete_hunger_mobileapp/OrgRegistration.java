package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrgRegistration extends AppCompatActivity {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://byete-hunger-application-default-rtdb.firebaseio.com/");

    // validate password if it has atleast 8 minimum characters, 1 Alphabet, 1 Number & 1 Special Character
    public static boolean isValidPassword(final String Password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_registration);

        TextView PrivacyPolicy = (TextView) this.findViewById(R.id.textView_OrgRegistration_PrivacyPolicy);
        TextView LoginHere = (TextView) this.findViewById(R.id.textView6_OrgRegistration_LoginHere);
        EditText Organization = (EditText) findViewById(R.id.editText_OrgRegistration_Organization);
        EditText ContactPerson = (EditText) findViewById(R.id.editText_OrgRegistration_ContactPerson);
        EditText ContactNo = (EditText) findViewById(R.id.editText_OrgRegistration_Contact);
        EditText Location = (EditText) findViewById(R.id.editText_OrgRegistration_Location);
        EditText EmailAddress = (EditText) findViewById(R.id.editText_OrgRegistration_EmailAdd);
        EditText Password = (EditText) findViewById(R.id.editText_OrgRegistration_Password);
        Button Register = (Button) findViewById(R.id.button3_OrgRegistration_Register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Organizationtxt = Organization.getText().toString();
                String ContactPersontxt = ContactPerson.getText().toString();
                String ContactNotxt = ContactNo.getText().toString();
                String Locationtxt = Location.getText().toString();
                String EmailAddresstxt = EmailAddress.getText().toString();
                String Passwordtxt = Password.getText().toString();

                if (Organizationtxt.isEmpty() || ContactPersontxt.isEmpty() || ContactNotxt.isEmpty() || Locationtxt.isEmpty() || EmailAddresstxt.isEmpty() || Passwordtxt.isEmpty()){
                    Toast.makeText(OrgRegistration.this, "Please enter your complete account details.", Toast.LENGTH_LONG).show();
                }else if(Passwordtxt.length() < 8 && !isValidPassword(Passwordtxt)){
                    Toast.makeText(OrgRegistration.this, "Your password must have a minimum of 8 characters, and should include atleast 1 letter of the alphabet, 1 number & 1 Special Character.", Toast.LENGTH_LONG).show();
                }else{
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(EmailAddresstxt)){
                                Toast.makeText(OrgRegistration.this, "Email address is already registered, please use a different one." , Toast.LENGTH_LONG).show();
                            }else{
                                dbref.child("users").child(EmailAddresstxt).child("Organization").setValue(Organizationtxt);
                                dbref.child("users").child(EmailAddresstxt).child("ContactPerson").setValue(ContactPersontxt);
                                dbref.child("users").child(EmailAddresstxt).child("ContactNo").setValue(ContactNotxt);
                                dbref.child("users").child(EmailAddresstxt).child("Location").setValue(Locationtxt);
                                dbref.child("users").child(EmailAddresstxt).child("Password").setValue(Passwordtxt);

                                Toast.makeText(OrgRegistration.this, "Registration Successful, Client Verification is underway." , Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(OrgRegistration.this, LoginScreen.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        // redirects to the privacy policy screen
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrgRegistration.this, PrivacyPolicy.class);
                startActivity(intent);
            }

        });

        // redirects to the login screen
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgRegistration.this, LoginScreen.class);
                startActivity(intent);
            }

        });

    }
}