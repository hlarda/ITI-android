package com.example.mvc_room_retrofit.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity (tableName = "products_table")
public class Product{
    @PrimaryKey
    public int id;

    public String title;
    public String description;
    public String category;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public String brand;
    public String sku;
    public int weight;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public String returnPolicy;
    public int minimumOrderQuantity;
    public String thumbnail;
}
