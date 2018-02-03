package com.example.alfasunny.homeuser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ManageRestaurant extends AppCompatActivity {
    static int stateCode = 1;
    Button btnGiveReward;
    Button btnGiveDiscount;
    Activity activity;
    DataHelper d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_restaurant);

        d = DataHelper.getInstance();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                }
            }
        });

        btnGiveReward = (Button) findViewById(R.id.btnGiveReward);
        btnGiveDiscount = (Button) findViewById(R.id.btnGiveDiscount);
        activity = this;

        btnGiveReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateCode=1;
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Detect User from QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        btnGiveDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateCode=2;
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Detect User from QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            if(result.getContents()==null) {
                Toast.makeText(this, "User Detection cancelled", Toast.LENGTH_LONG).show();
            } else {
                String userUID = result.getContents();
                if(stateCode==1) {
                    Intent giveRewardIntent = new Intent(ManageRestaurant.this, GiveRewardToUser.class);
                    giveRewardIntent.putExtra("uid", userUID);
                    startActivity(giveRewardIntent);
                }
                else {
                    Intent giveDiscountIntent = new Intent(ManageRestaurant.this, RedeemRewardFromUser.class);
                    giveDiscountIntent.putExtra("uid", userUID);
                    startActivity(giveDiscountIntent);
                }
//                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
