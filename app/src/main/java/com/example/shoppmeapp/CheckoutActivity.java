package com.example.shoppmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import com.google.firebase.database.DatabaseReference;

public class CheckoutActivity extends AppCompatActivity {
    private EditText nameEditText, numberEditText;
    private TextView totalTextView;

    private List<CartItem> cartItemsList;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        numberEditText = findViewById(R.id.numberEditText);
        totalTextView = findViewById(R.id.checkout_total);

        // Get the total from the intent extra
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0);

        // Display the total in the TextView
        totalTextView.setText("Total: $" + String.format("%.2f", totalAmount));

        // Get the cart items list from the intent
        cartItemsList = (List<CartItem>) getIntent().getSerializableExtra("cartItems");

        // Set click listener for Pay button
        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String number = numberEditText.getText().toString().trim();

                if (name.isEmpty() || number.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this, "Please enter your name and number.", Toast.LENGTH_SHORT).show();
                } else {
                    saveOrderToFirebase(name, number);
                }
            }
        });

    }

    private void saveOrderToFirebase(String name, String number) {
        // Create a Firebase Realtime Database reference and store the data
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shop-me-80213-default-rtdb.firebaseio.com/users");

        // Store cart items, total amount, user's name, and number under the user's unique ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = databaseRef.child(userId);

        // Step 1: Create a CartModel object with the cart items and total amount
        CartModel cartModel = new CartModel(cartItemsList, totalAmount);

        // Step 2: Save the CartModel to Firebase
        userRef.child("cartModel").setValue(cartModel);

        // Step 3: Save user's name and number
        userRef.child("name").setValue(name);
        userRef.child("number").setValue(number);

        // Show a success message or navigate to another activity after storing the data
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CheckoutActivity.this, OrderConfirmationActivity.class));
        finish();
    }
}