package com.example.byete_hunger_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    FirebaseAuth fAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fAuth = FirebaseAuth.getInstance();
        ImageView GoBack = (ImageView) this.findViewById(R.id.imageView5_ForgotPass_BackImage);
        TextView BackToLogin = (TextView) this.findViewById(R.id.textView2_ForgotPass_BackToLogin);
        Button Send = (Button) this.findViewById(R.id.button_ForgotPass_Send);
        EditText forEmail = (EditText) this.findViewById(R.id.editText_Forgot_EmailAddress);

        //Forgot Password
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = forEmail.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(forgotPassword.this, "Please provide your e-mail", Toast.LENGTH_SHORT).show();
                }else {
                    forgotpassword();
                }

            }
        });


        // Go Back to Login
        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotPassword.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        // Back to Previous Page
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void forgotpassword() {

        fAuth.getInstance();

        fAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotPassword.this, LoginScreen.class));
                            finish();
                        }else {
                            Toast.makeText(forgotPassword.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}