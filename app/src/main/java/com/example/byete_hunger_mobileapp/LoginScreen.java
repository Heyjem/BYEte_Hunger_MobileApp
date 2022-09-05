package com.example.byete_hunger_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreen extends AppCompatActivity {



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
                String Passwordtxt = EmailAddress.getText().toString();

                if (EmailAddresstxt.isEmpty() || Passwordtxt.isEmpty()){
                    Toast.makeText(LoginScreen.this, "Please enter your Email and Password.", Toast.LENGTH_LONG).show();
                }else{
                    //betlog
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