package com.example.alfasunny.homeuser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText email;
    EditText name;
    EditText phone;
    EditText password;
    RadioGroup accountType;
    Intent returnIntent;
    Button btnLogin;
    Button btnRegister;
    FirebaseAuth mAuth;
    public static final int LOGIN_REQUEST = 3;
    ProgressDialog registeringdialog;
    FirebaseDatabase db;
    DatabaseReference users;
    DatabaseReference summary;
    String nameVal;
    String emailVal;
    String phoneVal;
    String passwordVal;
    String accountTypeVal;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        returnIntent = getIntent();
        setResult(RESULT_CANCELED, returnIntent);

        email = (EditText) findViewById(R.id.inputEmail);
        name = (EditText) findViewById(R.id.inputName);
        phone = (EditText) findViewById(R.id.inputPhone);
        password = (EditText) findViewById(R.id.inputPassword);
        accountType = (RadioGroup) findViewById(R.id.inputAccountTypes);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        db = FirebaseDatabase.getInstance();
        users = db.getReference().child("users");
        summary = db.getReference().child("summary");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Register.this, Login.class);
                startActivityForResult(loginIntent, LOGIN_REQUEST);
//                Toast.makeText(Register.this, "going to login", Toast.LENGTH_LONG).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            boolean isEmpty(String s) {
                return TextUtils.isEmpty(s);
            }

            @Override
            public void onClick(View v) {
                nameVal = name.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                phoneVal = phone.getText().toString().trim();
                passwordVal = password.getText().toString().trim();
                if(accountType.getCheckedRadioButtonId() == R.id.owner) accountTypeVal = "owner";
                else accountTypeVal = "customer";

                if(isEmpty(emailVal) || isEmpty(passwordVal) || isEmpty(nameVal) || isEmpty(phoneVal)) {
                    Toast.makeText(Register.this, "Enter All The Required Values", Toast.LENGTH_LONG).show();
                    return;
                }

                registeringdialog = new ProgressDialog(Register.this);
                registeringdialog.setMessage("Creating Account...");
                registeringdialog.show();

                mAuth = FirebaseAuth.getInstance();


                mAuth.createUserWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Registration successfull", Toast.LENGTH_LONG).show();
                                    setResult(RESULT_OK, returnIntent);
                                    registeringdialog.cancel();
                                    setupUserData();
                                    finish();
                                }
                                else {
                                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();
                                    registeringdialog.cancel();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_REQUEST) {
            if(resultCode==RESULT_OK) {
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            else {
                Toast.makeText(Register.this, "Login Failed", Toast.LENGTH_LONG).show();

            }
        }
    }

    String getFullKey(String key) {
        return "/" + uid + "/" + key;
    }

    public void setupUserData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            uid = currentUser.getUid();
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(nameVal).build();
            currentUser.updateProfile(request);

            Map<String, Object> profileData = new HashMap<>();
            profileData.put(getFullKey("name"), nameVal);
            profileData.put(getFullKey("email"), emailVal);
            profileData.put(getFullKey("phone"), phoneVal);
            profileData.put(getFullKey("accountType"), accountTypeVal);
            users.updateChildren(profileData);

            Map<String, Object> summaryData = new HashMap<>();
            summaryData.put(getFullKey("totalEarning"), 0);
            summaryData.put(getFullKey("totalRedeem"), 0);
            summaryData.put(getFullKey("totalPoints"), 0);
            summary.updateChildren(summaryData);

            DatabaseReference sellerbase = users.child(uid);
            Map<String, Object> sellerData = new HashMap<>();
            sellerData.put("restaurantName", "Unnamed Restaurant");
            sellerData.put("restaurantLocation", "Unknown Location");
            sellerData.put("restaurantPhone", "Number not given");
            sellerData.put("restaurantRewardRate", 5.00);
            sellerData.put("restaurantMaximumDiscount", 100.00);

            sellerbase.updateChildren(sellerData);
        }



    }
}
