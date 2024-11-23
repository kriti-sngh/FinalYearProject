package com.example.shoppmeapp;

import java.util.List;

public class CartModel {
    private List<CartItem> cartItems;
    private double totalAmount;

    public CartModel() {
        // Empty constructor required for Firebase
    }

    public CartModel(List<CartItem> cartItems, double totalAmount) {
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

