package com.example.fileupload.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fileupload.R;
import com.example.fileupload.acounts.LoginActivity;
import com.example.fileupload.acounts.UserProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageUpload;
    private ImageView mFileUpload;
    private TextView viewPdf;
    private TextView viewImage;
    private TextView mTime;
    StorageReference forestRef;

    BottomNavigationView bottomNavigationView;

    String downloadUrl = "";

    final static int PICK_PDF_CODE = 2342;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageUpload = findViewById(R.id.uploadimage);
        mFileUpload = findViewById(R.id.uploadfile);
        viewPdf = findViewById(R.id.open_file);
        viewImage = findViewById(R.id.open_image);
        mTime = findViewById(R.id.time);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        mImageUpload.setOnClickListener(this);
        mFileUpload.setOnClickListener(this);
        viewPdf.setOnClickListener(this);
        viewImage.setOnClickListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Opening the phone dialer Intent
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + "0700898437"));
                        startActivity(phoneIntent);
                        break;
                //Email intent on the bottom navigation ba

                    case R.id.page_2:
                        try {
                            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "mainajoseph964@gmail.com"));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                            startActivity(intent);
                        } catch(Exception e) {
                            Toast.makeText(MainActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        break;


                }
                return true;
            }
        });


        }


    //the logout menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if(id == R.id.action_profile){
            profile();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //Opening the user profile
    private void profile() {
        Intent userProfile = new Intent(MainActivity.this, UserProfile.class);
        startActivity(userProfile);
    }

    //Logging out Intent
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    //navigation intents
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.uploadfile:
                Intent fileupload = new Intent(MainActivity.this, UploadFileActivity.class);
                startActivity(fileupload);
                break;

            case R.id.uploadimage:
                Intent imageupload = new Intent(MainActivity.this, UploadImageActivity.class);
                startActivity(imageupload);
                break;

            case R.id.open_file:
                Intent openFile = new Intent(MainActivity.this, ViewPdfFilesActivity.class);
                startActivity(openFile);
                break;

            case R.id.open_image:
                Intent openImage = new Intent(MainActivity.this, ViewImageActivity.class);
                startActivity(openImage);
                break;



        }

        }


}

