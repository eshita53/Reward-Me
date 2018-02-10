package com.example.alfasunny.homeuser.completed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alfasunny.homeuser.More;
import com.example.alfasunny.homeuser.Profile;
import com.example.alfasunny.homeuser.R;
import com.example.alfasunny.homeuser.Reviews;
import com.example.alfasunny.homeuser.backend.DataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class Home extends AppCompatActivity {
    DataHelper d;

    boolean isHome = true;
    boolean isNotification = false;
    boolean isProfile = false;
    boolean isMore = false;
    static boolean ownership = false;
    static int tpoints = 0;

    Button btnManage;
    Button btnAdd;
    Button btnReviews;
    Button btnRedeem;
    CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Common task for many activities
        d = DataHelper.getInstance();


        //Tasks specific to this page
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRedeem = (Button) findViewById(R.id.btnRedeem);
        btnReviews = (Button) findViewById(R.id.btnReviews);
        btnManage = (Button) findViewById(R.id.btnManage);
        profilePic = (CircleImageView) findViewById(R.id.profilePic);


        new Thread(() -> {
            try {
                String oldAddress = "";
                while (true) {
                    String newAddress = d.getUserProfilePictureAddress();
                    if (newAddress != oldAddress) {
                        runOnUiThread(()->{
                            Glide.with(Home.this).load(newAddress).into(profilePic);
                        });
                        oldAddress = newAddress;
                    }
                    Thread.sleep(500);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


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

        ProgressDialog dialog = new ProgressDialog(Home.this);
        dialog.setTitle("Loading data...");
        dialog.show();


        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                }
            }
        });

        d.getUsers().child(d.getUid()).child("accountType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.cancel();
                String accountType = dataSnapshot.getValue(String.class);
                if (accountType != null && accountType.equals("owner")) {
                    ownership = true;
                    btnManage.setVisibility(View.VISIBLE);
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
                                tpoints = totalpoints;
                                totalPointsTxt.setText(totalpoints.toString());
                            }
                        });


                        sleep(100);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getBaseContext(), Home.class);
                if (!isHome) startActivity(homeIntent);
            }
        });

        findViewById(R.id.btnNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationIntent = new Intent(getBaseContext(), Notifications.class);
                if (!isNotification) startActivity(notificationIntent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getBaseContext(), Profile.class);
                if (!isProfile) startActivity(profileIntent);
            }
        });

        findViewById(R.id.btnMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreIntent = new Intent(getBaseContext(), More.class);
                if (!isMore) startActivity(moreIntent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ownership = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ownership == true) {
            btnManage.setVisibility(View.VISIBLE);
        }
        TextView totalPointsTxt = (TextView) findViewById(R.id.totalPoints);
        totalPointsTxt.setText(String.valueOf(tpoints));
    }
}
