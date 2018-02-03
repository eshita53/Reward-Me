package com.example.alfasunny.homeuser.completed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.alfasunny.homeuser.Home;
import com.example.alfasunny.homeuser.R;
import com.example.alfasunny.homeuser.Register;
import com.example.alfasunny.homeuser.backend.DataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static final int REGISTER_REQUEST = 1;
    public static final int HOME_REQUEST = 2;
    DataHelper dataHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();

                if(mUser!=null) {
                    Intent homeIntent = new Intent(MainActivity.this, Home.class);
                    startActivityForResult(homeIntent, HOME_REQUEST);
                    finish();
                }
                else {
                    Intent registerIntent = new Intent(MainActivity.this, Register.class);
                    startActivityForResult(registerIntent, REGISTER_REQUEST);
                    finish();
                }
            }
        };
    }

    protected void onResume() {
        super.onResume();
        Thread waitThread = new Thread(()->{
            try {
                Thread.sleep(2000);
                mAuth.addAuthStateListener(mAuthListener);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        waitThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
