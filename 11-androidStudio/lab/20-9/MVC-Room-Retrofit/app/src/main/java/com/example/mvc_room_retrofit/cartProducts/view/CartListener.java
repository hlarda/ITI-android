package com.example.mvc_room_retrofit.cartProducts.view;

import com.example.mvc_room_retrofit.model.Product;

public interface CartListener {
    void onRemoveFromCartClicked(Product product);
}
