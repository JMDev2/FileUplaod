package com.example.fileupload.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileupload.R;
import com.example.fileupload.models.Constants;
import com.example.fileupload.models.FileUpload;
import com.example.fileupload.models.ImageUpload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private Context context;
    private List<FileUpload> fileDownloadList = new ArrayList<>();


    public FileAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public FileAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdfviewer, parent, false);
       FileViewHolder fileViewHolder = new FileViewHolder(view, fileDownloadList);
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.FileViewHolder holder, int position) {
        holder.bindFiles(fileDownloadList.get(position));
        holder.bindListener(fileDownloadList.get(position));




    }
    public void setFileList(List<FileUpload> fileUploadList){
        fileDownloadList = fileUploadList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return fileDownloadList.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName;
        private TextView fileDescription;
        private ImageView fileDelete;
        private ImageView fileDownload;

        private Context context;
        private ArrayList<FileUpload> downloadFile = new ArrayList<>();

        public FileViewHolder(@NonNull View itemView, List<FileUpload> fileDownloadList) {
            super(itemView);
            context = itemView.getContext();
            fileName = itemView.findViewById(R.id.file_name);
            fileDescription = itemView.findViewById(R.id.file_description);
            fileDelete = itemView.findViewById(R.id.file_delete);
            fileDownload = itemView.findViewById(R.id.file_download);
        }

        public void bindFiles(FileUpload fileUpload) {
            fileName.setText(fileUpload.getName());
            fileDescription.setText(fileUpload.getDescription());

        }

        public void bindListener(FileUpload fileUpload){
            fileDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Opening the upload file in browser using the upload url
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(fileUpload.getUrl()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            });


            //deleting the file from firebase
            fileDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(fileUpload.getID());
                    reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context, "File '" + fileUpload.getName() + "' Deleted", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(context, "Error when deleting", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            });


        }


    }

}
