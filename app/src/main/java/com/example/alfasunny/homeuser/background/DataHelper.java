package com.example.alfasunny.homeuser.background;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DataHelper() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dbref = db.getReference();
        users = dbref.child("users");
        transactions = dbref.child("transactions");
        summary = dbref.child("summary");
    }

    public String getName() {
        return mAuth.getCurrentUser().getDisplayName();
    }
}
