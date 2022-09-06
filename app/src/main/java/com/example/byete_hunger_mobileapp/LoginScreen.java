package com.example.byete_hunger_mobileapp;

import static com.example.byete_hunger_mobileapp.IndivRegistration.isValidPassword;

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

public class LoginScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_login_screen);

        TextView ForgotPassword = (TextView) this.findViewById(R.id.textView_LoginForgotPassword);
        TextView Register = (TextView) this.findViewById(R.id.textView4_Login_RegisterNow);
        Button Login = (Button) findViewById(R.id.button_Login);
        EditText EmailAddress = (EditText) findViewById(R.id.editText_LoginEmailAddress);
        EditText Password = (EditText) findViewById(R.id.editText_LoginPassword);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EmailAddresstxt = EmailAddress.getText().toString();
                String Passwordtxt = Password.getText().toString();

                if (EmailAddresstxt.isEmpty() || Passwordtxt.isEmpty()){
                    Toast.makeText(LoginScreen.this, "Please enter your Email and Password.", Toast.LENGTH_LONG).show();
                }else{
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(EmailAddresstxt)){
                                String getPassword = snapshot.child(EmailAddresstxt).child("Password").getValue(String.class);

                                if (getPassword.equals(Passwordtxt)){
                                    Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginScreen.this, Homescreen.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(LoginScreen.this, "Incorrect Password, Try Again.", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(LoginScreen.this, "Incorrect Email, Try Again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                }
        });


        // Redirect to Forgot Password Page
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, forgotPassword.class);
                startActivity(intent);
            }
        });

        // Redirect to Registration Page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegistrationMain.class);
                startActivity(intent);
            }
        });



    }
}