package com.example.mvp_room_retrofit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp_room_retrofit.allProducts.view.AllActivity;
import com.example.mvp_room_retrofit.cartProducts.view.CartActivity;

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