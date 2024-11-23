package com.example.shoppmeapp;


public class Model {
            private String imageUrl;
            private double price;

            public Model(String imageUrl, double price) {
                this.imageUrl = imageUrl;
                this.price = price;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }






