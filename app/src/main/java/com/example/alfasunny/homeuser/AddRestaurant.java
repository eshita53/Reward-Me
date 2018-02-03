package com.example.alfasunny.homeuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRestaurant extends AppCompatActivity {
    EditText tinNumber, restaurantName, restaurantLocation, restaurantPhone, restaurantRewardRatio;
    Button addRestaurantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        tinNumber = (EditText) findViewById(R.id.tinNumber);
        restaurantName = (EditText) findViewById(R.id.restaurantName);
        restaurantLocation = (EditText) findViewById(R.id.restaurantLocation);
        restaurantPhone = (EditText) findViewById(R.id.restaurantPhone);
        restaurantRewardRatio = (EditText) findViewById(R.id.restaurantRewardRate);
        addRestaurantInfo = (Button) findViewById(R.id.btnAddRestaurant);

        addRestaurantInfo.setOnClickListener(e->{
            String tin, name, location, phone, ratio;
            tin = tinNumber.getText().toString().trim();
            name = restaurantName.getText().toString().trim();
            location = restaurantLocation.getText().toString().trim();
            phone = restaurantPhone.getText().toString().trim();
            ratio = restaurantRewardRatio.getText().toString().trim();

            if(tin==null || tin.equals("") || name==null || name.equals("") || location==null || location.equals("") || phone==null || phone.equals("") || ratio==null || ratio.equals("")) {
                Toast.makeText(AddRestaurant.this, "Enter all the values first...", Toast.LENGTH_LONG).show();
            } else {
                Intent returnIntent = getIntent();
                setResult(RESULT_OK, returnIntent);
                returnIntent.putExtra("tin", tin);
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("location", location);
                returnIntent.putExtra("phone", phone);
                returnIntent.putExtra("ratio", ratio);
                finish();
            }
        });



    }
}
