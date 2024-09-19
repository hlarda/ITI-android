package com.example.mvc_room_retrofit.network;

import java.util.List;

public interface NetworkCallback {
    void onSuccess(List<Product> response);
    void onFailure(String error);
}
