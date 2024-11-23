package com.example.shoppmeapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.Button;
import com.google.gson.Gson;
import android.content.SharedPreferences;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import android.util.Log;



public class ViewCartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private ArrayList<CartItem> cartItems;
    private TextView totalTextView;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalTextView = findViewById(R.id.totalTextView);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Retrieve the cart items from the previous activity
        Intent intent = getIntent();
        cartItems = (ArrayList<CartItem>) intent.getSerializableExtra("cartItems");

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Set up the RecyclerView adapter
        adapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(adapter);

        // Calculate the total price and update the view
        updateTotalPrice();

        checkoutButton.setOnClickListener(v -> {
            // Handle the checkout button click here (e.g., start a new activity)
            // Add your code for the checkout process
            onCheckoutClick();
        });
    }


    public void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();

        // Display the total price in the TextView
        totalTextView.setText("Total: $" + String.format("%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    // Method to handle the checkout button click
    public void onCheckoutClick() {
        String total = totalTextView.getText().toString();
        Intent intent = new Intent(this, CheckoutActivity.class);
        // Pass the cart items to the CheckoutActivity
        intent.putExtra("cartItems", cartItems);
        intent.putExtra("totalAmount", total);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the cart items using SharedPreferences when the activity is paused
        saveCartItems();
    }

    private void saveCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartItems);

        editor.putString("cartItems", json);
        editor.apply();

        // Add debug log to check saved cart items
        Log.d("SavedCartItems", json);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve the cart items using SharedPreferences when the activity is resumed
        retrieveCartItems();
        updateTotalPrice();
    }

    private void retrieveCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartItems", "");

        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        cartItems = gson.fromJson(json, type);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Add debug log to check retrieved cart items
        for (CartItem item : cartItems) {
            Log.d("CartItem", "Image URL: " + item.getImageUrl() + ", Price: " + item.getPrice());
        }
    }


}