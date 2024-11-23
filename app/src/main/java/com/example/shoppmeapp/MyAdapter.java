package com.example.shoppmeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Model> imageList;
    private OnImageClickListener onImageClickListener;

    public MyAdapter(Context context, List<Model> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model imageItem = imageList.get(position);

        // Load the image using Glide
        Glide.with(context)
                .load(imageItem.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image) // Optional placeholder image while loading
                        .error(R.drawable.error_image)) // Optional error image in case of loading failure
                .into(holder.imageView);

        // Display the price alongside the image
        holder.priceTextView.setText("Price: $" + String.valueOf(imageItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView priceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onImageClickListener != null) {
                onImageClickListener.onImageClick(getAdapterPosition());
            }
        }
    }

    // Interface to handle clicks on images in the RecyclerView
    public interface OnImageClickListener {
        void onImageClick(int position);
    }
}