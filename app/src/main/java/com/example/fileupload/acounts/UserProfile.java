package com.example.fileupload.acounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fileupload.R;
import com.example.fileupload.models.Constants;
import com.example.fileupload.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    ImageView mProfilePicture;
    TextView mUserName;
    TextView mEmail;
    TextView mPhone;
    TextView mCountry;


    private FirebaseDatabase database;
    private String email;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        mProfilePicture = (ImageView) findViewById(R.id.user_image);
        mUserName = (TextView) findViewById(R.id.profilename);
        mEmail = (TextView) findViewById(R.id.user_email);
        mPhone = (TextView) findViewById(R.id.user_phone);
        mCountry = (TextView) findViewById(R.id.user_country);



        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.DATABASE_PROFILES).child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImage()).into(mProfilePicture);
                        mUserName.setText(user.getUserName());
                        mEmail.setText(user.getEmail());
                        mPhone.setText(user.getPhone());
                        mCountry.setText(user.getCountry());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}