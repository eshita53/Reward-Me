package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static final int REGISTER_REQUEST = 1;
    public static final int HOME_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            boolean restaurantOwner;
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();

                if(mUser!=null) {
                    Intent homeIntent = new Intent(MainActivity.this, Home.class);
                    startActivityForResult(homeIntent, HOME_REQUEST);
                }
                else {
//                    Toast.makeText(MainActivity.this, "Not logged in", Toast.LENGTH_LONG).show();
                    Intent registerIntent = new Intent(MainActivity.this, Register.class);
                    startActivityForResult(registerIntent, REGISTER_REQUEST);
                }
            }
        };
    }

    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==HOME_REQUEST) {
            finish();
        }
        if(requestCode==REGISTER_REQUEST) {
            if(resultCode==RESULT_OK) {
                Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
            }
            else {
                finish();
            }
        }
    }
}
