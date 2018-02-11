package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.AddReward;
import com.example.alfasunny.homeuser.completed.Home;
import com.example.alfasunny.homeuser.completed.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class Reviews extends AppCompatActivity {
    DataHelper d;
    RecyclerView reviewList;
    ArrayList<ReviewEach> reviewEachArrayList = new ArrayList();
    Button addReview;
    volatile boolean stop = false;
    CircleImageView profilePic;
    Drawable loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stop = false;

        setContentView(R.layout.activity_reviews);

        profilePic = (CircleImageView) findViewById(R.id.profilePic);
        loading = profilePic.getDrawable();

        reviewList = (RecyclerView) findViewById(R.id.reviewList);

        addReview = (Button) findViewById(R.id.btnAddReview);

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reviews.this, AddReview.class));
            }
        });

        d = DataHelper.getInstance();

        d.getReviews().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviewEachArrayList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ReviewEach current = child.getValue(ReviewEach.class);
                    reviewEachArrayList.add(current);
                }
                Collections.reverse(reviewEachArrayList);

                reviewList.setAdapter(new reviewAdapter(reviewEachArrayList, Reviews.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        d.getmAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    finish();
                }
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

        TextView name = (TextView) findViewById(R.id.displayName);
        name.setText(d.getName());

        new Thread(new Runnable() {
            Integer earned;
            Integer redeemed;
            Integer totalpoints;
            TextView earnedTxt;
            TextView redeemTxt;

            @Override
            public void run() {
                while (true) {
                    try {
                        earned = d.getTotalEarning();
                        earnedTxt = (TextView) findViewById(R.id.totalEarned);

                        redeemed = d.getTotalRedeem();
                        redeemTxt = (TextView) findViewById(R.id.totalRedeemed);

                        totalpoints = d.getTotalPoints();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                earnedTxt.setText(getString(R.string.earned_points) + earned.toString());
                                redeemTxt.setText(getString(R.string.redeemed_points) + redeemed.toString());
                            }
                        });


                        sleep(100);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stop = false;

        new Thread(new Runnable() {
            String oldAddress = "";
            String newAddress = "";

            @Override
            public void run() {
                try {

                    while (true) {
                        if (stop) return;

                        newAddress = d.getUserProfilePictureAddress();
                        if (!stop && newAddress != null && newAddress != oldAddress) {
                            runOnUiThread(() -> {
                                GlideApp.with(Reviews.this).load(newAddress).placeholder(loading).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        newAddress = "";
                                        return true;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(profilePic);
                            });
                            oldAddress = newAddress;
                        }
                        Thread.sleep(500);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop = true;
    }
}
