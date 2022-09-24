package com.uploads.fileupload.acounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uploads.fileupload.ui.MainActivity;
import com.uploads.fileupload.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mText;
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginButton;
    private ProgressBar mProgressBar;
    private TextView mFprgotPassowrd;
    private CardView loginDetails;

    private FirebaseAuth mAuth;
    private ProgressBar mProgressbar;
    private FirebaseAuth.AuthStateListener mAuthListener;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mText = (TextView) findViewById(R.id.signup_text);
        mLoginEmail = (EditText) findViewById(R.id.login_email);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBar);
        mFprgotPassowrd = (TextView) findViewById(R.id.forgot_password);
        loginDetails = (CardView) findViewById(R.id.logindetails);
        mText.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mFprgotPassowrd.setOnClickListener(this);


        mProgressbar.setVisibility(View.GONE);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        if(view == mText){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
        if(view == mLoginButton){
            userLogin();
        }
        if(view == mFprgotPassowrd){
            Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
            startActivity(intent);
        }




    }




    private void userLogin() {
        String LoginEmail = mLoginEmail.getText().toString().trim();
        String LoginPassword = mLoginPassword.getText().toString().trim();

        if (LoginEmail.isEmpty()){
            mLoginEmail.setError("Email should not be empty");
            mLoginEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(LoginEmail).matches()){
            mLoginEmail.setError("Please provide a valid email");
            mLoginEmail.requestFocus();
            return;
        }
        if(LoginPassword.isEmpty()){
            mLoginPassword.setError("Pleas provide a password");
            mLoginPassword.requestFocus();
            return;
        }
        if(LoginPassword.length() < 6){
            mLoginPassword.setError("Min password length is 6 characters");
            mLoginPassword.requestFocus();
            return;
        }
        mProgressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(LoginEmail, LoginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_LONG).show();
                    mProgressbar.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}