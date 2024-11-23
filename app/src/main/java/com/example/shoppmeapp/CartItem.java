package com.example.shoppmeapp;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String imageUrl;
    private int quantity;
    private double price;
    private boolean isSelected;

    public CartItem(String imageUrl, double price) {
        this.imageUrl = imageUrl;
        this.quantity = 1;
        this.price = price;
        this.isSelected = false;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
