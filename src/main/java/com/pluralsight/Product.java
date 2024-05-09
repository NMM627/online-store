package com.pluralsight;

public class Product {
    // private attributes
    private String id;
    private String productName;
    private double price;

    // constructor
    public Product(String id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}

