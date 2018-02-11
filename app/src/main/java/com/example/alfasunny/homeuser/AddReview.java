package com.example.alfasunny.homeuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfasunny.homeuser.backend.DataHelper;
import com.example.alfasunny.homeuser.completed.AddReward;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AddReview extends AppCompatActivity {
    EditText restaurantName, itemName, ratingData, reviewDescription;
    Button submitReview;
    DataHelper d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        restaurantName = (EditText) findViewById(R.id.restaurantName);
        itemName = (EditText) findViewById(R.id.itemName);
        ratingData = (EditText) findViewById(R.id.ratingData);
        reviewDescription = (EditText) findViewById(R.id.reviewDescription);
        submitReview = (Button) findViewById(R.id.submitReview);

        d = DataHelper.getInstance();
        

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurantNameText, itemNameText, ratingDataText, reviewDescriptionText;
                restaurantNameText = restaurantName.getText().toString();
                itemNameText = itemName.getText().toString();
                ratingDataText = ratingData.getText().toString();
                reviewDescriptionText = reviewDescription.getText().toString();

                if(restaurantNameText.equals("") || itemNameText.equals("") || ratingDataText.equals("") || reviewDescriptionText.equals("")) {
                    Toast.makeText(AddReview.this, "Enter all the values", Toast.LENGTH_LONG).show();
                    return;
                }

                d.getUsers().child(d.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String authorNameText = dataSnapshot.getValue(String.class);
                        ReviewEach currentReview = new ReviewEach(restaurantNameText, itemNameText, ratingDataText, authorNameText, reviewDescriptionText);
                        d.getReviews().push().setValue(currentReview);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
