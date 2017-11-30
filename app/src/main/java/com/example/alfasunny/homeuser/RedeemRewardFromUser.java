package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.google.firebase.auth.FirebaseAuth;

public class RedeemRewardFromUser extends AppCompatActivity {
    DataHelper d;
    Button btnRedeemword;
    EditText inputRedeemVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_reward_from_user);

        d = new DataHelper();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                }
            }
        });

        btnRedeemword = (Button) findViewById(R.id.btnRedeemReward);
        inputRedeemVal = (EditText) findViewById(R.id.inputValue);

        btnRedeemword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.redeemReward(getIntent().getStringExtra("uid"), Integer.parseInt(inputRedeemVal.getText().toString()));
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
