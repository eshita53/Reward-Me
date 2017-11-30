package com.example.alfasunny.homeuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alfasunny.homeuser.background.DataHelper;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataHelper d = new DataHelper();

        TextView name = (TextView) findViewById(R.id.displayName);
        name.setText(d.getName());

        Integer earned = d.getTotalEarning();
        TextView earnedTxt = (TextView) findViewById(R.id.totalEarned);
        earnedTxt.setText("Earned Points: " + earned.toString());

        Integer redeemed = d.getTotalRedeem();
        TextView redeemTxt = (TextView) findViewById(R.id.totalRedeemed);
        redeemTxt.setText("Redeemed Points: " + redeemed.toString());

    }
}
