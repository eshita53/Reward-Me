package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alfasunny.homeuser.background.DataHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Thread.sleep;

public class Home extends AppCompatActivity {
    DataHelper d;

    Button btnManage;
    Button btnAdd;
    Button btnReviews;
    Button btnRedeem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Tasks specific to this page
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRedeem = (Button) findViewById(R.id.btnRedeem);
        btnReviews = (Button) findViewById(R.id.btnReviews);
        btnManage = (Button) findViewById(R.id.btnManage);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getBaseContext(), AddReward.class);
                startActivity(addIntent);
            }
        });

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redeemIntent = new Intent(getBaseContext(), RedeemReward.class);
                startActivity(redeemIntent);
            }
        });

        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewsIntent = new Intent(getBaseContext(), Reviews.class);
                startActivity(reviewsIntent);
            }
        });

        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageIntent = new Intent(getBaseContext(), ManageRestaurant.class);
                startActivity(manageIntent);
            }
        });



        //Common task for many activities
        d = new DataHelper();

        d.getUsers().child(d.getUid()).child("accountType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String accountType = dataSnapshot.getValue(String.class);
                if(accountType.equals("owner")==false) {
                    btnManage.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView name = (TextView) findViewById(R.id.displayName);
        name.setText(d.getName());

        new Thread(new Runnable() {
            Integer earned;
            Integer redeemed;
            Integer totalpoints;
            TextView earnedTxt;
            TextView redeemTxt;
            TextView totalPointsTxt;
            @Override
            public void run() {
                while (true) {
                    try {
                        earned = d.getTotalEarning();
                        earnedTxt = (TextView) findViewById(R.id.totalEarned);

                        redeemed = d.getTotalRedeem();
                        redeemTxt = (TextView) findViewById(R.id.totalRedeemed);

                        totalpoints = d.getTotalPoints();
                        totalPointsTxt = (TextView) findViewById(R.id.totalPoints);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                earnedTxt.setText(getString(R.string.earned_points) + earned.toString());
                                redeemTxt.setText(getString(R.string.redeemed_points) + redeemed.toString());
                                totalPointsTxt.setText(totalpoints.toString());
                            }
                        });


                        sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
