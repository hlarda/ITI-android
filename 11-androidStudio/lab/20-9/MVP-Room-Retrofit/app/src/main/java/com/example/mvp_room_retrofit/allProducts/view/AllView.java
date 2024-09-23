package com.example.mvp_room_retrofit.allProducts.view;

import com.example.mvp_room_retrofit.model.Product;

import java.util.List;

public interface AllView {
    void showProducts(List<Product> products);
    void showError(String error);
}