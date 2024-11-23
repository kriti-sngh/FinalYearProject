package com.example.shoppmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScrollViewActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        // Find the button by its ID
        Button buttonNext = findViewById(R.id.buttonNew);

        // Set click listener for the button
        buttonNext.setOnClickListener(v -> {
            // Start the NextActivity when the button is clicked
            Intent intent = new Intent(ScrollViewActivity.this, ImageDisplay.class);
            startActivity(intent);
        });

        }
}

