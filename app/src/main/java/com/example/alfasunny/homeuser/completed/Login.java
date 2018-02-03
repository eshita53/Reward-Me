package com.example.alfasunny.homeuser.completed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfasunny.homeuser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressDialog loggingInDialog;
    Intent returnIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        returnIntent = getIntent();
        setResult(RESULT_CANCELED, returnIntent);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            boolean isEmpty(String s) {
                return TextUtils.isEmpty(s);
            }
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString().trim();
                String passwordVal = password.getText().toString().trim();
                if(isEmpty(emailVal) || isEmpty(passwordVal)) {
                    Toast.makeText(Login.this, "Enter your email and password", Toast.LENGTH_LONG).show();
                    return;
                }

                loggingInDialog = new ProgressDialog(Login.this);
                loggingInDialog.setMessage("Logging in");
                loggingInDialog.show();

                mAuth.signInWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    setResult(RESULT_OK, returnIntent);
                                    loggingInDialog.cancel();
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    loggingInDialog.cancel();
                                }
                            }
                        });
            }
        });
    }
}
