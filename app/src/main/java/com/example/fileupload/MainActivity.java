package com.example.fileupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mUploadImage;
    private EditText mFileName;
    private FloatingActionButton mSubmitButton;
    private TextView status;
    private TextView textView;
    private ImageView mImageUpload;

    String downloadUrl = "";

    final static int PICK_PDF_CODE = 2342;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUploadImage = (ImageView) findViewById(R.id.uploadfile);
        mFileName = (EditText) findViewById(R.id.file_name);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.upload_button);
        status = (TextView) findViewById(R.id.status);
        textView = (TextView) findViewById(R.id.textView);
        mImageUpload = (ImageView) findViewById(R.id.uploadimage);

        mUploadImage.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        textView.setOnClickListener(this);
        mImageUpload.setOnClickListener(this);

        mSubmitButton.setEnabled(false);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
    }

    //this function will get the pdf from the storage
//    private void getPDF() {
//        //for greater than lolipop versions we need the permissions asked on runtime
//        //so if the permission is not available user will go to the screen to allow storage permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//            return;
//        }
//        //creating an intent for file chooser
//
//
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //when the user choses the file
//        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            //if a file is selected
//            if (data.getData() != null) {
//                //uploading the file
//                uploadFile(data.getData());
//            }else{
//                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void uploadFile(Uri data) {
//        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
//        sRef.putFile(data)
//        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                status.setText("File FileUpload Successfully");
//
//                FileUpload upload = new FileUpload(mFileName.getText().toString(), taskSnapshot.getStorage().getDownloadUrl().toString());
//                mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @SuppressWarnings("VisibleForTests")
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        status.setText((int) progress + "% Uploading...");
//                    }
//                });
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.uploadfile:
//                Intent intent = new Intent();
//                intent.setType("application/pdf");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
//
//                break;

            case R.id.uploadfile:
                Intent fileupload = new Intent(MainActivity.this, UploadFileActivity.class);
                startActivity(fileupload);
                break;

            case R.id.textView:
                Intent i = new Intent(MainActivity.this, ViewPdfActivity.class);
                startActivity(i);
                break;

            case R.id.uploadimage:
                Intent imageupload = new Intent(MainActivity.this, UploadImageActivity.class);
                startActivity(imageupload);
                break;



        }
}
}