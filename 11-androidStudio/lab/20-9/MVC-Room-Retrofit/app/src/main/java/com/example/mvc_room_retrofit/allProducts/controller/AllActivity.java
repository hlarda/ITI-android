package com.example.mvc_room_retrofit.allProducts.controller;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvc_room_retrofit.R;
import com.example.mvc_room_retrofit.allProducts.view.AllAdapter;
import com.example.mvc_room_retrofit.allProducts.view.AllListener;
import com.example.mvc_room_retrofit.db.ProductDAO;
import com.example.mvc_room_retrofit.db.ProductDatabase;
import com.example.mvc_room_retrofit.model.Product;
import com.example.mvc_room_retrofit.network.NetworkCallback;
import com.example.mvc_room_retrofit.network.ProductClient;

import java.util.List;

public class AllActivity extends AppCompatActivity implements NetworkCallback, AllListener {
    RecyclerView allRecyclerView;
    RecyclerView.LayoutManager allLayoutManager;
    AllAdapter allAdapter;
    List<Product> cartList;

    ProductDatabase productDatabase;
    ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.all_products);

        allRecyclerView = findViewById(R.id.allRecyclerView);
        allLayoutManager = new LinearLayoutManager(AllActivity.this, LinearLayoutManager.VERTICAL, false);
        allRecyclerView.setLayoutManager(allLayoutManager);

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
        allAdapter = new AllAdapter(AllActivity.this, response, this);
        allRecyclerView.setAdapter(allAdapter);
    }

    @Override
    public void onFailure(String error) {
        Log.i(TAG, "onFailure: " + error);
    }

    @Override
    public void onAddToCartClicked(Product product) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                productDAO.insert(product);
            }
        }).start();
    }
}
