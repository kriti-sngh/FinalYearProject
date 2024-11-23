package com.example.shoppmeapp;

import java.util.ArrayList;
public class CartManager {
    private static CartManager instance;
    private ArrayList<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

}
