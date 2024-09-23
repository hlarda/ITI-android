package com.example.mvp_room_retrofit.network;


import com.example.mvp_room_retrofit.model.Product;

import java.util.List;

public interface NetworkCallback {
    void onSuccess(List<Product> response);
    void onFailure(String error);
}
