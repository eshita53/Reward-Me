package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.MainActivity;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;

public class More extends AppCompatActivity {
    DataHelper d;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        d =DataHelper.getInstance();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                }
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.getmAuth().signOut();
                startActivity(new Intent(More.this, MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.logoutSection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.getmAuth().signOut();
                startActivity(new Intent(More.this, MainActivity.class));
                finish();
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
    }
}
