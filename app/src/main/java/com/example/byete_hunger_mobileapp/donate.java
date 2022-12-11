package com.example.byete_hunger_mobileapp;

import static java.text.DateFormat.getDateTimeInstance;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.byete_hunger_mobileapp.databinding.ActivityDonateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class donate extends AppCompatActivity {

    Spinner spinner;
    Button Submit;
    ImageView back, account, chooseImage, datepurchasedCal, expiredCal;
    EditText weight, datePurchased, dateExpired, contactNo, location, notes;
    DatabaseReference dbRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    int mDate, mMonth, mYear;
    Timer timer;
    ActivityResultLauncher<String> launcher;
    boolean pushBool;
    SharedPreferences getPushNotif;

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
        location = findViewById(R.id.et_donate_location);
        notes = findViewById(R.id.et_donate_notes);
        Submit = findViewById(R.id.button4_donate_submit);
        datepurchasedCal = findViewById(R.id.datepurchased_image);
        expiredCal = findViewById(R.id.expired_image);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        String uid = currentUser.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DonationType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Pop-up calendar when calendar image is clicked
        datepurchasedCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mDate = cal.get(Calendar.DATE);
                mMonth = cal.get(Calendar.MONTH);
                mYear = cal.get(Calendar.YEAR);
                DatePickerDialog dpg = new DatePickerDialog(donate.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                        datePurchased.setText(DD + "-" + (MM+1) + "-" + YYYY);
                    }
                },mYear,mMonth,mDate);
                dpg.show();
            }
        });

        //Pop-up calendar when calendar image is clicked
        expiredCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal2 = Calendar.getInstance();
                mDate = cal2.get(Calendar.DATE);
                mMonth = cal2.get(Calendar.MONTH);
                mYear = cal2.get(Calendar.YEAR);
                DatePickerDialog dpg2 = new DatePickerDialog(donate.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                        dateExpired.setText(DD + "-" + (MM+1) + "-" + YYYY);
                    }
                },mYear,mMonth,mDate);
                dpg2.show();
            }
        });

        //Pop-up calendar when edittext is clicked
        datePurchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal3 = Calendar.getInstance();
                mDate = cal3.get(Calendar.DATE);
                mMonth = cal3.get(Calendar.MONTH);
                mYear = cal3.get(Calendar.YEAR);
                DatePickerDialog dpg3 = new DatePickerDialog(donate.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                        datePurchased.setText(DD + "-" + (MM+1) + "-" + YYYY);
                    }
                },mYear,mMonth,mDate);
                dpg3.show();
            }
        });

        //Pop-up calendar when edittext is clicked
        dateExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal4 = Calendar.getInstance();
                mDate = cal4.get(Calendar.DATE);
                mMonth = cal4.get(Calendar.MONTH);
                mYear = cal4.get(Calendar.YEAR);
                DatePickerDialog dpg4 = new DatePickerDialog(donate.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                        dateExpired.setText(DD + "-" + (MM+1) + "-" + YYYY);
                    }
                },mYear,mMonth,mDate);
                dpg4.show();
            }
        });

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

        //choose image in files
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });


        dbRef.child("Users").child(uid).child("contactNo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Cn = dataSnapshot.getValue(String.class);
                contactNo.setText(Cn);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dbRef.child("Users").child(uid).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String loc = dataSnapshot.getValue(String.class);
                location.setText(loc);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
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
                        if(pushBool){
                            notification();
                        }

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
                        String loc = location.getText().toString();
                        String nts = notes.getText().toString();
                        String id = dbRef.push().getKey();
                        String imageUrl = "";
                        String donationStatus = "Pending";

                        //show when new card was added to donations
                        Date date = new Date();
                        Date time = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss a");
                        String dateAdded = formatter.format(date);
                        String dateAddedTime = formatter2.format(time);

                        Map map = new HashMap();
                        map.put("timestamp", ServerValue.TIMESTAMP);

                        donation Donation = new donation(type, wt, dP, dE, cN, loc, nts, id, dateAdded, dateAddedTime, imageUrl, donationStatus, ServerValue.TIMESTAMP);

                        //insert donation details to firebase realtime database
                        dbRef.child("Users").child(uid).child("donation").child(id).setValue(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(donate.this,"Donation details inserted", Toast.LENGTH_LONG).show();

                                    HashMap<String,Object> hashMap = new HashMap<>();
                                    hashMap.put("uid",uid);

                                    dbRef.child("Users").child(uid).child("donation").child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                        }
                                    });

                                    //insert current users fullname, contactperson, and organization details to firebase realtime database
                                    dbRef.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String cP = dataSnapshot.child("contactPerson").getValue(String.class);
                                            String fN = dataSnapshot.child("fullName").getValue(String.class);
                                            String org = dataSnapshot.child("organization").getValue(String.class);

                                            HashMap<String,Object> Fn = new HashMap<>();
                                            HashMap<String,Object> Cp = new HashMap<>();
                                            HashMap<String,Object> Org = new HashMap<>();
                                            Cp.put("contactPerson",cP);
                                            Fn.put("fullName",fN);
                                            Org.put("organization",org);

                                            dbRef.child("Users").child(uid).child("donation").child(id).updateChildren(Fn);
                                            dbRef.child("Users").child(uid).child("donation").child(id).updateChildren(Cp);
                                            dbRef.child("Users").child(uid).child("donation").child(id).updateChildren(Org);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                }
                            }
                        });

                        // firebase storage folder location
                        StorageReference riversRef = storageRef.child("images/").child(uid).child(dateAdded).child(id + "." + GetFileExtension(result));

                        // uploads image to firebase storage
                        riversRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = uri.toString();
                                        Donation.setImageUrl(url);
                                        dbRef.child("Users").child(uid).child("donation").child(id).child("imageUrl").setValue(Donation.getImageUrl());
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Image Uploaded.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), "Failed to Upload Image.", Toast.LENGTH_LONG).show());

                    }
                });
            }
        });

    }

    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void notification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notif", "notif", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Notif")
                .setContentTitle("Donation Submitted")
                .setSmallIcon(R.drawable.client_logo_png)
                .setAutoCancel(true)
                .setContentText("Donation in process, kindly wait for completion to submit a new donation.");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());

    }


    @Override
    protected void onResume() {
        super.onResume();
        getPushNotif = PreferenceManager.getDefaultSharedPreferences(this);
        pushBool =  getPushNotif.getBoolean("pushNotif", true);
    }


}