package com.example.shoppmeapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the button by its ID
        Button buttonNext = findViewById(R.id.buttonNext);

        // Set click listener for the button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the NextActivity when the button is clicked
                Intent intent = new Intent(HomeActivity.this, ImageDisplay.class);
                startActivity(intent);
            }
        });
    }
}
