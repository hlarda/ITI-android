package com.example.mvc_room_retrofit;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvc_room_retrofit.db.ProductDAO;
import com.example.mvc_room_retrofit.db.ProductDatabase;
import com.example.mvc_room_retrofit.network.NetworkCallback;
import com.example.mvc_room_retrofit.model.Product;
import com.example.mvc_room_retrofit.network.ProductClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkCallback {
    ProductDatabase productDatabase;
    ProductDAO productDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ProductClient productClient = ProductClient.getInstance();
        productClient.getProducts(this);

        productDatabase = ProductDatabase.getInstance(this);
        productDAO      = productDatabase.getProductDAO();
    }

    @Override
    public void onSuccess(List<Product> response) {
        Log.i(TAG, "onSuccess: ");
        for(Product product: response){
            Log.i(TAG, "ID: " + product.id);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                productDAO.insert(response.get(0));
                Log.i(TAG, "After Insertion:");
                for(Product product: productDAO.getProducts()){
                    Log.i(TAG, "ID: " + product.id);
                }
            }
        }).start();

    }

    @Override
    public void onFailure(String error) {
        Log.i(TAG, "onFailure: " + error);
    }
}