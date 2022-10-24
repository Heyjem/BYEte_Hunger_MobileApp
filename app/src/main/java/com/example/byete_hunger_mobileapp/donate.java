package com.example.byete_hunger_mobileapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.byete_hunger_mobileapp.databinding.ActivityDonateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit;
    ImageView back, account, chooseImage;
    EditText weight, datePurchased, dateExpired, contactNo, notes;
    DatabaseReference dbRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    Timer timer;
    ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        chooseImage = findViewById(R.id.donate_uploadImage);
        back = findViewById(R.id.donate_back_button);
        account = findViewById(R.id.donate_account_page_icon);
        recyclerView = findViewById(R.id.rv_donationstracker);
        spinner = findViewById(R.id.spinner_donate);
        weight = findViewById(R.id.et_donate_weight);
        datePurchased = findViewById(R.id.et_donate_donatePurchased);
        dateExpired = findViewById(R.id.et_donate_donateExpired);
        contactNo = findViewById(R.id.et_donate_contactNo);
        notes = findViewById(R.id.et_donate_notes);
        Submit = findViewById(R.id.button4_donate_submit);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(donate.this, Account.class));
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                chooseImage.setImageURI(result);

                Submit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        InsertData();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }

                    public void InsertData() {
                        String type = spinner.getSelectedItem().toString();
                        String wt = weight.getText().toString();
                        String dP = datePurchased.getText().toString();
                        String dE = dateExpired.getText().toString();
                        String cN = contactNo.getText().toString();
                        String nts = notes.getText().toString();
                        String id = dbRef.push().getKey();
                        String imageUrl = "";



                        //show when new card was added to donations
                        Date date = new Date();
                        Date time = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
                        String dateAdded = formatter.format(date);
                        String dateAddedTime = formatter2.format(time);

                        donation Donation = new donation(type, wt, dP, dE, cN, nts, id, dateAdded, dateAddedTime, imageUrl);

                        dbRef.child("Users").child(currentUser.getUid()).child("donation").child(id).setValue(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(donate.this,"Donation details inserted", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        // random uid name in fireabase storage
                        StorageReference riversRef = storageRef.child("images/").child(currentUser.getUid()).child(date.toString());

                        // uploads image to firebase storage
                        riversRef.child(id + "." + GetFileExtension(result)).putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //donation Donation = new donation(uri.toString());
                                        dbRef.child("Users").child(currentUser.getUid()).child("donation").child(id).child("image").setValue(Donation);
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Image Uploaded.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), "Failed to Upload Image.", Toast.LENGTH_LONG).show());
                    }

                });
            }
        });

        //choose image in files
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
    }

    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}