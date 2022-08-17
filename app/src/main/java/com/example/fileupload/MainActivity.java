package com.example.fileupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageUpload;
    private ImageView mFileUpload;

    String downloadUrl = "";

    final static int PICK_PDF_CODE = 2342;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageUpload = (ImageView) findViewById(R.id.uploadimage);
        mFileUpload = (ImageView) findViewById(R.id.uploadfile);

        mImageUpload.setOnClickListener(this);
        mFileUpload.setOnClickListener(this);



        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
    }



    //the logout methods
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

    private void profile() {
        Intent userProfile = new Intent(MainActivity.this, UserProfile.class);
        startActivity(userProfile);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


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



        }
}
}