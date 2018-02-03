package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class RedeemRewardFromUser extends AppCompatActivity {
    DataHelper d;
    Button btnRedeemword;
    EditText inputRedeemVal;
    DatabaseReference summary;
     TextView availablePoints;
     FirebaseAuth mAuth;
     String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_reward_from_user);
        mAuth=FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        summary= FirebaseDatabase.getInstance().getReference("summary");
        d = DataHelper.getInstance();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                }
            }
        });

        availablePoints=(TextView) findViewById(R.id.availablePoints);

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
        summary.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  totalEarning = dataSnapshot.child("totalEarning").getValue(Integer.class);
              //  totalRedeem = dataSnapshot.child("totalRedeem").getValue(Integer.class);
                availablePoints.setText(Integer.toString((int)dataSnapshot.
                        child("totalPoints").getValue(Integer.class)));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//      int totalPoints= d.getTotalPoints();




    }
}
