package com.example.mvc_room_retrofit;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvc_room_retrofit.allProducts.controller.AllActivity;
import com.example.mvc_room_retrofit.cartProducts.controller.CartActivity;
import com.example.mvc_room_retrofit.db.ProductDAO;
import com.example.mvc_room_retrofit.db.ProductDatabase;
import com.example.mvc_room_retrofit.network.NetworkCallback;
import com.example.mvc_room_retrofit.model.Product;
import com.example.mvc_room_retrofit.network.ProductClient;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    Button allBtn, cartBtn, exitBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        allBtn = findViewById(R.id.allBtn);
        cartBtn = findViewById(R.id.cartBtn);
        exitBtn = findViewById(R.id.exitBtn);

        allBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AllActivity.class);
            startActivity(intent);
        });
        cartBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
        exitBtn.setOnClickListener(view -> moveTaskToBack(true));
    }
}