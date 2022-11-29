package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends AppCompatActivity {

    EditText EmailAddress, Password;
    TextView ForgotPassword, Register;
    Button Login;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        ForgotPassword = findViewById(R.id.textView_LoginForgotPassword);
        Register = findViewById(R.id.textView4_Login_RegisterNow);
        Login = findViewById(R.id.button_Login);
        EmailAddress = findViewById(R.id.editText_LoginEmailAddress);
        Password = findViewById(R.id.editText_LoginPassword);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                }
        });


        // Redirect to Forgot Password Page
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, forgotPassword.class));
            }
        });

        // Redirect to Registration Page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, RegistrationMain.class));
            }
        });
    }

    private void login(){
        String user = EmailAddress.getText().toString().trim();
        String pass = Password.getText().toString().trim();
        if(user.isEmpty()){
            EmailAddress.setError("Please enter your email.");
        }
        if(pass.isEmpty()){
            Password.setError("Please enter your password");
        }else{
            fAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this, Homescreen.class));
                        finish();
                    }else{
                        Toast.makeText(LoginScreen.this, "Login Unsuccessful" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

}