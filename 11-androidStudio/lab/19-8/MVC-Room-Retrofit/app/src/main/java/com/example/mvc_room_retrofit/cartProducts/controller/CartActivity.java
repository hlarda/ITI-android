package com.example.mvc_room_retrofit.cartProducts.controller;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvc_room_retrofit.R;
import com.example.mvc_room_retrofit.cartProducts.view.CartAdapter;
import com.example.mvc_room_retrofit.cartProducts.view.CartListener;
import com.example.mvc_room_retrofit.db.ProductDAO;
import com.example.mvc_room_retrofit.db.ProductDatabase;
import com.example.mvc_room_retrofit.model.Product;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener {
    RecyclerView cartRecyclerView;
    RecyclerView.LayoutManager cartLayoutManager;
    CartAdapter cartAdapter;
    LiveData<List<Product>> cartList;

    ProductDatabase productDatabase;
    ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        cartRecyclerView.setLayoutManager(cartLayoutManager);

        productDatabase = ProductDatabase.getInstance(this);
        productDAO      = productDatabase.getProductDAO();


        cartList = productDAO.getProducts();
        cartList.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                cartAdapter = new CartAdapter(CartActivity.this, products, CartActivity.this);
                cartRecyclerView.setAdapter(cartAdapter);
            }
        });
    }

    @Override
    public void onRemoveFromCartClicked(Product product) {
        new Thread(() -> {
            productDAO.deleteProduct(product);
        }).start();
    }
}