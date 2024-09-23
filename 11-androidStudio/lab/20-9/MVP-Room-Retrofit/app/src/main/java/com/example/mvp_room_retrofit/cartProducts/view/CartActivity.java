package com.example.mvp_room_retrofit.cartProducts.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp_room_retrofit.R;
import com.example.mvp_room_retrofit.cartProducts.presenter.CartPresenter;
import com.example.mvp_room_retrofit.model.Product;
import com.example.mvp_room_retrofit.db.ProductRepository;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartView, CartListener {
    RecyclerView cartRecyclerView;
    RecyclerView.LayoutManager cartLayoutManager;
    CartAdapter cartAdapter;
    CartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        cartRecyclerView.setLayoutManager(cartLayoutManager);

        ProductRepository productRepository = new ProductRepository(this);
        presenter = new CartPresenter(this, productRepository);
        presenter.loadCartProducts();
    }

    @Override
    public void showCartProducts(List<Product> products) {
        Log.i(TAG, "showCartProducts: ");
        cartAdapter = new CartAdapter(CartActivity.this, products, this);
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }

    @Override
    public void onRemoveFromCartClicked(Product product) {
        presenter.removeFromCart(product);
    }
}