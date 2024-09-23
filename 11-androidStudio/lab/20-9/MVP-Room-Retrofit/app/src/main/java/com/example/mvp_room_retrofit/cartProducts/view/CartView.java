package com.example.mvp_room_retrofit.cartProducts.view;

import com.example.mvp_room_retrofit.model.Product;

import java.util.List;

public interface CartView {
    void showCartProducts(List<Product> products);
    void showError(String error);
}
