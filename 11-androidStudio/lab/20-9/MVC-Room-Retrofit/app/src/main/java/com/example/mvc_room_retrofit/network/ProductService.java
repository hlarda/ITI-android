package com.example.mvc_room_retrofit.network;

import com.example.mvc_room_retrofit.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("products")
    Call<Root> getProducts();
}
