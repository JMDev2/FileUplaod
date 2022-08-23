package com.example.fileupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signupText;
    private EditText mUserName;
    private EditText mEmail;
    private EditText mCountry;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mButton;
    private ProgressBar mSignUpProgressBar;
    private ImageView mSignupImage;

    private FirebaseAuth mAuth;
    StorageReference storageReference;

    private static int RESULT_LOAD_IMG = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signupText = (TextView) findViewById(R.id.signup_text);
        mUserName = (EditText) findViewById(R.id.signup_username);
        mEmail = (EditText) findViewById(R.id.signup_email);
        mCountry = (EditText) findViewById(R.id.signup_country1);
        mPhone = (EditText) findViewById(R.id.signup_phone);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.signup_confirmpassword);
        mButton = (Button) findViewById(R.id.login_button);
        mSignUpProgressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        mSignupImage = (ImageView) findViewById(R.id.signup_image);

        signupText.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mSignupImage.setOnClickListener(this);

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
        if (view == mSignupImage){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mSignupImage.setImageURI(imageUri);

                uploadImage(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateAccountActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(CreateAccountActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage(Uri imageUri) {
        StorageReference fileRef = storageReference.child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CreateAccountActivity.this, "success", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateAccountActivity.this, "failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUser() {
        String myUserName = mUserName.getText().toString().trim();
        String myEmail = mEmail.getText().toString().trim();
        String myCountry = mCountry.getText().toString().trim();
        String myPhone = mPhone.getText().toString().trim();
        String myPassword = mPassword.getText().toString().trim();
        String myConfirmPassword = mConfirmPassword.getText().toString().trim();

        int myImage = mSignupImage.getImageAlpha();


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
        if(myCountry.isEmpty()){
            mCountry.setError("Country Required");
            mCountry.requestFocus();
            return;
        }

        if(myPhone.isEmpty()){
            mPhone.setError("City Required");
            mPhone.requestFocus();
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
                    User user = new User(myUserName, myEmail, myCountry, myPhone, myImage);

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