package com.example.shoppmeapp;

public class MySingleton {

    private static MySingleton sInstance;

    // Private constructor to prevent direct instantiation from outside the class
    private MySingleton() {
        // Initialization code here (if needed)
    }

    // Method to get the instance of the singleton
    public static MySingleton getInstance() {
        if (sInstance == null) {
            // If sInstance is null, create a new instance
            sInstance = new MySingleton();
        }
        return sInstance;
    }

    // Other methods and variables of the singleton class
}

