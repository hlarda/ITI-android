package com.example.retrofitjson;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterfce {
    @GET("products")
    Call<Root> getProducts();
}
