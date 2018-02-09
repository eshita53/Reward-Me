package com.example.alfasunny.homeuser.backend;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanmoy Krishna Das on 30-Nov-17.
 */

public class DataHelper {
    private static DataHelper instance = new DataHelper();

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
    private FirebaseStorage mFirebaseStorage;
    private StorageReference storage;

    String userName, userEmail, userPhone, userProfilePictureAddress;

    public static DataHelper getInstance() {
        return instance;
    }

    public StorageReference getStorage() {
        return storage;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserProfilePictureAddress() {
        return userProfilePictureAddress;
    }

    private DataHelper() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        storage = mFirebaseStorage.getReference();
        dbref = db.getReference();
        users = dbref.child("users");
        transactions = dbref.child("transactions");
        summary = dbref.child("summary");

        uid = mAuth.getCurrentUser().getUid();

        summary.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalEarning = dataSnapshot.child("totalEarning").getValue(Integer.class);
                totalRedeem = dataSnapshot.child("totalRedeem").getValue(Integer.class);
                totalPoints = dataSnapshot.child("totalPoints").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        users.child(getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("name").getValue(String.class);
                userPhone = dataSnapshot.child("phone").getValue(String.class);
                userEmail = dataSnapshot.child("email").getValue(String.class);
                userProfilePictureAddress = dataSnapshot.child("profilePicAddress").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        transactions.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer sum=0, earned=0, redeemed=0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Integer val = snapshot.child("amount").getValue(Integer.class);

                    sum+=val;
                    if(val>0) earned+=val;
                    else redeemed-=val;
                }

                Map<String, Object> mp = new HashMap<>();
                mp.put("/totalEarning", earned);
                mp.put("/totalRedeem", redeemed);
                mp.put("/totalPoints", sum);
                summary.child(uid).updateChildren(mp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addReward(String customerId, int points, double cost) {
        DatabaseReference customerTransaction = transactions.child(customerId);
        String key = customerTransaction.push().getKey();
        Map<String, Object> transactionData = new HashMap<>();
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionData.put("/" + key + "/" + "toPersonName", dataSnapshot.child(customerId).child("name").getValue(String.class));
                transactionData.put("/" + key + "/" + "fromRestaurantName", dataSnapshot.child(uid).child("restaurant").child("Name").getValue(String.class));
                transactionData.put("/" + key + "/" + "to", customerId);
                transactionData.put("/" + key + "/" + "cost", cost);
                transactionData.put("/" + key + "/" + "from", uid);
                transactionData.put("/" + key + "/" + "amount", points);
                customerTransaction.updateChildren(transactionData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public UploadTask uploadImage(Uri uri) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return storage.child("images").child(currentTime).child(uri.getLastPathSegment()).putFile(uri);
    }

    public void redeemReward(String customerId, int points, double cost) {
        addReward(customerId, (0 - points), cost);
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
        return totalRedeem;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
