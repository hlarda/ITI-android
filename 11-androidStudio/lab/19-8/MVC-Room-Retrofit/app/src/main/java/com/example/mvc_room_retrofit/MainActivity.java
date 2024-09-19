package com.example.mvc_room_retrofit;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mvc_room_retrofit.network.NetworkCallback;
import com.example.mvc_room_retrofit.network.Product;
import com.example.mvc_room_retrofit.network.ProductClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ProductClient productClient = ProductClient.getInstance();
        productClient.getProducts(this);
    }

    @Override
    public void onSuccess(List<Product> response) {
        for(Product product: response){
            Log.i(TAG, "ID: " + product.id);
        }
    }

    @Override
    public void onFailure(String error) {
        Log.i(TAG, "onFailure: " + error);
    }
}