package com.example.alfasunny.homeuser.background;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tanmoy Krishna Das on 30-Nov-17.
 */

public class DataHelper {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    DatabaseReference dbref;
    DatabaseReference users;
    DatabaseReference transactions;
    DatabaseReference summary;
    Integer totalEarning = 0;
    Integer totalRedeem = 0;
    Integer totalPoints = 0;
    String uid;

    public DataHelper() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dbref = db.getReference();
        users = dbref.child("users");
        transactions = dbref.child("transactions");
        summary = dbref.child("summary");
        uid = mAuth.getCurrentUser().getUid();

        summary.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalEarning =  dataSnapshot.child("totalEarning").getValue(Integer.class);
                totalRedeem = dataSnapshot.child("totalRedeem").getValue(Integer.class);
                totalPoints =  dataSnapshot.child("totalPoints").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public FirebaseDatabase getDb() {
        return db;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public DatabaseReference getDbref() {
        return dbref;
    }

    public DatabaseReference getUsers() {
        return users;
    }

    public DatabaseReference getTransactions() {
        return transactions;
    }

    public DatabaseReference getSummary() {
        return summary;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return mAuth.getCurrentUser().getDisplayName();
    }

    public int getTotalEarning() {
        return totalEarning;
    }

    public int getTotalRedeem() {
        return  totalRedeem;
    }

    public int getTotalPoints() {
        return  totalPoints;
    }
}
