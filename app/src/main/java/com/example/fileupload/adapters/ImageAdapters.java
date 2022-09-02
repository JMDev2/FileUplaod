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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fileupload.R;
import com.example.fileupload.models.FileUpload;
import com.example.fileupload.models.ImageUpload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUpload upload = imageUploadList.get(position);

                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUploadList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView imageName;
        private TextView imageDescription;
        private ImageView imagedelete;
        private ImageView imageDownload;

        private Context context;

        public ImageViewHolder(@NonNull View itemView, List<ImageUpload> imageUploadList) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            imageDescription = itemView.findViewById(R.id.image1_description);
            imagedelete = itemView.findViewById(R.id.file_delete);
            imageDownload = itemView.findViewById(R.id.image_download);

        }

        public void bindImages(ImageUpload imageUpload) {

            Picasso.get().load(imageUpload.getUrl()).into(image);
            imageName.setText(imageUpload.getName());
            imageDescription.setText(imageUpload.getDescription());
        }
    }
}
