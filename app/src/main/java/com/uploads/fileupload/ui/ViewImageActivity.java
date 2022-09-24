package com.uploads.fileupload.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.uploads.fileupload.R;
import com.uploads.fileupload.adapters.ImageAdapters;
import com.uploads.fileupload.models.Constants;
import com.uploads.fileupload.models.ImageUpload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageAdapters imageAdapters;
    List<ImageUpload> uploadList = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        recyclerView =(RecyclerView) findViewById(R.id.image_recyclerview);
        progressBar = findViewById(R.id.progressBarImagerecycler);
        floatingActionButton = findViewById(R.id.floatingActionButtonImage);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewImageActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });

        progressBar.setVisibility(View.VISIBLE);




        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS_images).child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        //retrieving upload data from firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uploadList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ImageUpload upload = postSnapshot.getValue(ImageUpload.class);
                    uploadList.add(upload);
                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadList.get(i).getName();
                }

                //displaying it to list
                imageAdapters = new ImageAdapters(getApplicationContext(),uploadList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(imageAdapters);
                recyclerView.setHasFixedSize(true);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


