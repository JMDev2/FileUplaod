package com.example.fileupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mProfilePicture = (ImageView) findViewById(R.id.user_image);
        mUserName = (TextView) findViewById(R.id.user_name);
        mEmail = (TextView) findViewById(R.id.user_email);
        mPhone = (TextView) findViewById(R.id.user_phone);
        mCountry = (TextView) findViewById(R.id.user_country);


        databaseReference = FirebaseDatabase.getInstance().getReference("profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapShot : snapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);

                    mUserName.setText(user.getUserName());
                    mEmail.setText(user.getEmail());
                    mCountry.setText(user.getCountry());
                    mPhone.setText(user.getPhone());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}