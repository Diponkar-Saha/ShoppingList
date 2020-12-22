package com.example.shoppingcost;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private TextView registrationTV;
    private Button loginBT;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailET = findViewById(R.id.email_login);
        passwordET = findViewById(R.id.pass_login);
        loginBT = findViewById(R.id.button_login);
        registrationTV = findViewById(R.id.registrationTV);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // counterTV.setText("No of attempts remaining :5");
        if (currentUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));


        }


        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                if (email.isEmpty()) {
                    emailET.setError("Email Required");
                    emailET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordET.setError("Password Required");
                    passwordET.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordET.setError("Password Should be atleast six character");
                    passwordET.requestFocus();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                                } else {
                                    progressbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }
                        });
            }


        });
        registrationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                finish();
            }
        });

    }
}