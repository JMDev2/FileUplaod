package com.example.fileupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signupText;
    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mButton;
    private ProgressBar mSignUpProgressBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signupText = (TextView) findViewById(R.id.signup_text);
        mUserName = (EditText) findViewById(R.id.signup_username);
        mEmail = (EditText) findViewById(R.id.signup_email);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.signup_confirmpassword);
        mButton = (Button) findViewById(R.id.login_button);
        mSignUpProgressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        signupText.setOnClickListener(this);
        mButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mSignUpProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(view == signupText){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if(view == mButton){
            registerUser();
        }
    }

    private void registerUser() {
        String myUserName = mUserName.getText().toString().trim();
        String myEmail = mEmail.getText().toString().trim();
        String myPassword = mPassword.getText().toString().trim();
        String myConfirmPassword = mConfirmPassword.getText().toString().trim();

        if (myUserName.isEmpty()){
            mUserName.setError("User Name Required!");
            mUserName.requestFocus();
            return;
        }
        if (myEmail.isEmpty()){
            mEmail.setError("Email required");
            mEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(myEmail).matches()){
            mEmail.setError("Please provide a valid email");
            mEmail.requestFocus();
            return;
        }
        if(myPassword.isEmpty()){
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }
        if(myPassword.length() < 6){
            mPassword.setError("Min Password length should be 6 characters");
            mPassword.requestFocus();
            return;
        }

        mSignUpProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(myUserName, myEmail);

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(CreateAccountActivity.this, "user has been registered", Toast.LENGTH_LONG).show();
                                        mSignUpProgressBar.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(CreateAccountActivity.this, "Failed to regsiter", Toast.LENGTH_LONG).show();
                                        mSignUpProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }
            }
        });


    }
}