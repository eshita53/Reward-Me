package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    DataHelper d;
    Button editButton;
    TextView name;
    TextView email;
    TextView phone;
    CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        d = DataHelper.getInstance();
        name = (TextView) findViewById(R.id.name_id_textView);
        phone = (TextView) findViewById(R.id.phone_id_textView);
        email = (TextView) findViewById(R.id.email_id_textview);
        profilePic = (CircleImageView) findViewById(R.id.profilePic);


        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                }
            }
        });

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getBaseContext(), Home.class);
                startActivity(homeIntent);
            }
        });

        findViewById(R.id.btnNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationIntent = new Intent(getBaseContext(), Notifications.class);
                startActivity(notificationIntent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getBaseContext(), Profile.class);
                startActivity(profileIntent);
            }
        });

        findViewById(R.id.btnMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreIntent = new Intent(getBaseContext(), More.class);
                startActivity(moreIntent);
            }
        });
        findViewById(R.id.edit_button).setOnClickListener((v) -> {
            Intent profileToEditProfile = new Intent(Profile.this, ProfileEditPage.class);
            startActivity(profileToEditProfile);


        });

        name.setText(d.getUserName());
        phone.setText(d.getUserPhone());
        email.setText(d.getUserEmail());

        new Thread(() -> {
            try {
                String oldAddress = "";
                while (true) {
                    String newAddress = d.getUserProfilePictureAddress();
                    if (newAddress != null && newAddress != oldAddress) {
                        runOnUiThread(()->{
                            //Toast.makeText(Profile.this, newAddress, Toast.LENGTH_LONG).show();
                            Glide.with(Profile.this).load(newAddress).into(profilePic);
                        });
                        oldAddress = newAddress;
                    }
                    Thread.sleep(500);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
