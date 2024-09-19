package com.example.mvc_room_retrofit.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {
    private static final String BASE_URL = "https://dummyjson.com/";
    private final ProductService productService;
    private static ProductClient client;

    private ProductClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productService = retrofit.create(ProductService.class);
    }

    public static ProductClient getInstance(){
        if (client == null){
            client = new ProductClient();
        }
        return client;
    }

    public void getProducts(NetworkCallback callback){
        Call<Root> call = productService.getProducts();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body().products);
                }else {
                    callback.onFailure("Failed to get products");
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

}
