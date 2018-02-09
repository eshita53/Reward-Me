package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GiveRewardToUser extends AppCompatActivity {
    Button btnAddReward;
    EditText rewardPoint,inputRewardRatio, price;
    DataHelper d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_reward_to_user);

        price = (EditText) findViewById(R.id.inputPrice);
        btnAddReward = (Button) findViewById(R.id.btnAddReward);
        rewardPoint = (EditText) findViewById(R.id.inputReward);
        inputRewardRatio=(EditText) findViewById(R.id.inputRewardRatio);

        d = DataHelper.getInstance();

        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                }
            }
        });

        d.getUsers().child(d.getUid()).child("restaurant").child("RewardRate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                inputRewardRatio.setText(String.valueOf(dataSnapshot.getValue(Double.class)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnAddReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int reward = Integer.parseInt(rewardPoint.getText().toString());
                double cost = Double.parseDouble(price.getText().toString());
                d.addReward(getIntent().getStringExtra("uid"), reward, cost);

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
