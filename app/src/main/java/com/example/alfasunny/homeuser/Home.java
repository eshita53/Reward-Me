package com.example.alfasunny.homeuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alfasunny.homeuser.background.DataHelper;

import static java.lang.Thread.sleep;

public class Home extends AppCompatActivity {
    DataHelper d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        d = new DataHelper();

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
                                earnedTxt.setText("Earned Points: " + earned.toString());
                                redeemTxt.setText("Redeemed Points: " + redeemed.toString());
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
