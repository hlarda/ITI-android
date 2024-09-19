package com.example.mvc_room_retrofit.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("products")
    Call<Root> getProducts();
}
