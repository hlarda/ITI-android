package com.example.downloadimghandler;

public class Product {
    private String title;
    private String description;
    private String price;
    private String brand;
    private String rating;
    private String imageUrl;

    public Product(String title, String description, String price, String brand, String rating, String imageUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getBrand() { return brand; }
    public String getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }

}
