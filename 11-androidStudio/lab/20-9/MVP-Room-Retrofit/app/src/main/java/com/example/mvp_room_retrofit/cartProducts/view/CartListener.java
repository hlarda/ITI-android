package com.example.mvp_room_retrofit.cartProducts.view;

import com.example.mvp_room_retrofit.model.Product;

public interface CartListener {
    void onRemoveFromCartClicked(Product product);
}
