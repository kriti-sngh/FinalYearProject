package com.example.shoppmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private FirebaseAuth mAuth; // Declare the FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth instance

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        signUpButton = findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Check if passwords match before proceeding
                if (!password.equals(confirmPassword)) {
                    showToast("Passwords do not match");
                    return;
                }

                // Create a new user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign up success, update UI (optional)
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // You can perform further actions, like saving user data to the database, etc.
                                    showToast("Sign Up Successful!");
                                    // Proceed to the next activity or perform any other action
                                } else {
                                    // If sign up fails, display a message to the user.
                                    String errorMessage = task.getException().getMessage();
                                    showToast("Sign Up Failed: " + errorMessage);
                                }
                            }
                        });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}