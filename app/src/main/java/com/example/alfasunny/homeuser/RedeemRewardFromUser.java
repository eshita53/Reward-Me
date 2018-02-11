package com.example.alfasunny.homeuser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RedeemRewardFromUser extends AppCompatActivity {
    DataHelper d;
    Button btnRedeemReward;
    EditText inputRedeemVal;
    EditText inputPrice;
    DatabaseReference summary;
    TextView availablePoints;
    FirebaseAuth mAuth;
    String uid;
    ProgressDialog dialog;
    Integer available;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_reward_from_user);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        summary = FirebaseDatabase.getInstance().getReference("summary");
        d = DataHelper.getInstance();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                }
            }
        });


        availablePoints = (TextView) findViewById(R.id.availablePoints);
        inputPrice = (EditText) findViewById(R.id.inputPrice);

        btnRedeemReward = (Button) findViewById(R.id.btnRedeemReward);
        inputRedeemVal = (EditText) findViewById(R.id.inputValue);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading data");
        dialog.show();

        String customerID = getIntent().getStringExtra("uid");
        d.getSummary().child(customerID).child("totalPoints").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.cancel();
                available = dataSnapshot.getValue(Integer.class);
                availablePoints.setText(String.valueOf(available));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnRedeemReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerId = getIntent().getStringExtra("uid");
                int redeemPoints = Integer.parseInt(inputRedeemVal.getText().toString());

                if (redeemPoints > available) {
                    Toast.makeText(RedeemRewardFromUser.this, "enter a lower value of redeemed reward", Toast.LENGTH_LONG).show();
                    return;
                }

                Double cost = Double.parseDouble(inputPrice.getText().toString());
                d.redeemReward(customerId, redeemPoints, cost);
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
        summary.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  totalEarning = dataSnapshot.child("totalEarning").getValue(Integer.class);
                //  totalRedeem = dataSnapshot.child("totalRedeem").getValue(Integer.class);
                availablePoints.setText(Integer.toString(dataSnapshot.child("totalPoints").getValue(Integer.class)));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//      int totalPoints= d.getTotalPoints();


    }
}
