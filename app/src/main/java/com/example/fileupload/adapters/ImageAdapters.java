package com.example.fileupload.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fileupload.R;
import com.example.fileupload.models.Constants;
import com.example.fileupload.models.FileUpload;
import com.example.fileupload.models.ImageUpload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageAdapters extends RecyclerView.Adapter<ImageAdapters.ImageViewHolder> {
    private Context context;
    private List<ImageUpload> imageUploadList = new ArrayList<>();

    public ImageAdapters(Context context, List<ImageUpload> imageUploadList) {
        this.context = context;
        this.imageUploadList = imageUploadList;
    }

    @NonNull
    @Override
    public ImageAdapters.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageviewer, parent,false);
       ImageViewHolder imageViewHolder = new ImageViewHolder(view, imageUploadList);
       return imageViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapters.ImageViewHolder holder, int position) {
        holder.bindImages(imageUploadList.get(position));
        holder.bindImageListener(imageUploadList.get(position));

    }

    @Override
    public int getItemCount() {
        return imageUploadList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView imageName;
        private TextView imageDescription;
        private ImageView imageDelete;
        private ImageView imageDownload;

        private Context context;

        public ImageViewHolder(@NonNull View itemView, List<ImageUpload> imageUploadList) {
            super(itemView);
            context = itemView.getContext();
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            imageDescription = itemView.findViewById(R.id.image1_description);
            imageDelete = itemView.findViewById(R.id.image_delete);
            imageDownload = itemView.findViewById(R.id.image_download);

        }

        public void bindImages(ImageUpload imageUpload) {

            Picasso.get().load(imageUpload.getUrl()).into(image);
            imageName.setText(imageUpload.getName());
            imageDescription.setText(imageUpload.getDescription());
        }

        public void bindImageListener(ImageUpload imageUpload) {
            imageDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Opening the upload file in browser using the upload url
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(imageUpload.getUrl()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS_images).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(imageUpload.getRef());

                    reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context, "File '" + imageUpload.getName() + "' Deleted", Toast.LENGTH_LONG).show();
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
