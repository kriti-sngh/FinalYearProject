package com.example.shoppmeapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ImageDisplay extends AppCompatActivity {
   private RecyclerView recyclerView;
   private MyAdapter adapter;
   private ArrayList<Model> imageList;
   private ArrayList<CartItem> cartItems;
   private ActivityResultLauncher<Intent> imagePickerLauncher;

   private static final int REQUEST_IMAGE_PICKER = 1001;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_image_display);

      recyclerView = findViewById(R.id.recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      imageList = new ArrayList<>();
      adapter = new MyAdapter(this, imageList);
      recyclerView.setAdapter(adapter);

      // Initialize the cartItems list
      cartItems = new ArrayList<>();

      // Call a method to retrieve images from Firebase Storage
      retrieveImagesFromFirebase();

      // Set an OnClickListener for the "Upload Image" button
      Button buttonUploadImage = findViewById(R.id.buttonUploadImage);
      buttonUploadImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            openImagePicker();
         }
      });

      // Initialize the ActivityResultLauncher
      imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
              new ActivityResultCallback<ActivityResult>() {
                 @Override
                 public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                       Intent data = result.getData();
                       if (data != null && data.getData() != null) {
                          Uri imageUri = data.getData();
                          // Process the selected image here
                          // You can use the imageUri to get the selected image
                          // and perform any processing or display logic as needed
                       }
                    }
                 }
              });

      // Set an OnImageClickListener for the RecyclerView items
      adapter.setOnImageClickListener(new MyAdapter.OnImageClickListener() {
         @Override
         public void onImageClick(int position) {
            // Get the selected image from the list
            Model selectedItem = imageList.get(position);

            // Check if the item is already in the cart
            boolean foundInCart = false;
            for (CartItem item : cartItems) {
               if (item.getImageUrl().equals(selectedItem.getImageUrl())) {
                  // If found in cart, increase the quantity
                  item.increaseQuantity();
                  foundInCart = true;
                  break;
               }
            }

            // If not found in cart, add it as a new item
            if (!foundInCart) {
               CartItem newCartItem = new CartItem(selectedItem.getImageUrl(), 100); // Assuming price is 100 for each image
               cartItems.add(newCartItem);
            }

            // Show a toast message indicating that the image was added to the cart
            Toast.makeText(ImageDisplay.this, "Image added to cart", Toast.LENGTH_SHORT).show();
         }
      });

      // Set an OnClickListener for the "View Cart" button
      Button buttonViewCart = findViewById(R.id.buttonViewCart);
      buttonViewCart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (cartItems.isEmpty()) {
               // Show a toast message indicating that the cart is empty
               Toast.makeText(ImageDisplay.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            } else {
               // Start the ViewCartActivity to view the cart contents
               Intent intent = new Intent(ImageDisplay.this, ViewCartActivity.class);
               intent.putExtra("cartItems", cartItems);
               startActivity(intent);
            }
         }
      });
   }

   private void retrieveImagesFromFirebase() {
      // Replace "your-firebase-project-id" with your actual Firebase project ID
      String storageBucketUrl = "gs://shop-me-80213.appspot.com";

      // Replace "Image" with the appropriate directory in Firebase Storage where your images are stored
      StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(storageBucketUrl).child("Image");

      storageRef.listAll().addOnSuccessListener(listResult -> {
         for (StorageReference item : listResult.getItems()) {
            item.getDownloadUrl().addOnSuccessListener(uri -> {
               // Add the image URL and price to the list
               imageList.add(new Model(uri.toString(), 100));
               // Notify the adapter that data has changed
               adapter.notifyDataSetChanged();
            }).addOnFailureListener(e -> {
               // Handle any errors that occurred during image retrieval
               Toast.makeText(ImageDisplay.this, "Error loading images", Toast.LENGTH_SHORT).show();
            });
         }
      }).addOnFailureListener(e -> {
         // Handle any errors that occurred during listAll() operation
         Toast.makeText(ImageDisplay.this, "Error fetching image list", Toast.LENGTH_SHORT).show();
      });
   }


   private void openImagePicker() {
      // Create an intent to pick an image from the device's gallery
      Intent intent = new Intent(Intent.ACTION_PICK);
      intent.setType("image/*");
      imagePickerLauncher.launch(intent);
   }
}
