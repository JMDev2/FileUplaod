package com.example.fileupload.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fileupload.R;
import com.example.fileupload.adapters.FileAdapter;
import com.example.fileupload.models.Constants;
import com.example.fileupload.models.FileUpload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPdfFilesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FileAdapter fileAdapter;
    List<FileUpload> uploadList = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_files);

        uploadList = new ArrayList<>();

        recyclerView = findViewById(R.id.view_pdf_recyclerView);



        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        //retrieving upload data from firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FileUpload upload = postSnapshot.getValue(FileUpload.class);
                    uploadList.add(upload);
                }

                //displaying it to list
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
//                listView.setAdapter(adapter);

                fileAdapter = new FileAdapter(getApplicationContext());
                fileAdapter.setFileList(uploadList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(fileAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}




