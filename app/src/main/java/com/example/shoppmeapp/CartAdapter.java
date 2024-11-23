package com.example.shoppmeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<CartItem> cartItems;

    public CartAdapter(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image) // Optional placeholder image while loading
                        .error(R.drawable.error_image)) // Optional error image in case of loading failure
                .into(holder.imageView);

        // Set the quantity text
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        // Set the checkbox state based on the item's selection status
        holder.checkBox.setChecked(item.isSelected());

        // Set the click listener for the checkbox to update the selection status
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setSelected(isChecked);
            // Calculate the total price and update the view
            ViewCartActivity viewCartActivity = (ViewCartActivity) holder.itemView.getContext();
            viewCartActivity.updateTotalPrice();

            // Notify the adapter that the item has changed
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView quantityTextView;
        CheckBox checkBox;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_item_image);
            quantityTextView = itemView.findViewById(R.id.cart_item_quantity);
            checkBox = itemView.findViewById(R.id.cart_item_checkbox);
        }
    }
}