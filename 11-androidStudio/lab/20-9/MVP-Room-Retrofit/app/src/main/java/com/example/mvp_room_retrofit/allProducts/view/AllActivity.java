package com.example.mvp_room_retrofit.allProducts.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp_room_retrofit.R;
import com.example.mvp_room_retrofit.allProducts.presenter.AllPresenter;
import com.example.mvp_room_retrofit.model.Product;
import com.example.mvp_room_retrofit.db.ProductRepository;
import com.example.mvp_room_retrofit.network.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AllActivity extends AppCompatActivity implements AllView, AllListener {
    RecyclerView allRecyclerView;
    RecyclerView.LayoutManager allLayoutManager;
    AllAdapter   allAdapter;
    AllPresenter presenter;

    private static final int CHECK_INTERVAL = 500;
    private Handler handler;
    private Snackbar snackbar;
    private boolean productsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.all_products);

        allRecyclerView = findViewById(R.id.allRecyclerView);
        allLayoutManager = new LinearLayoutManager(AllActivity.this, LinearLayoutManager.VERTICAL, false);
        allRecyclerView.setLayoutManager(allLayoutManager);

        presenter = new AllPresenter(this, new ProductRepository(this)); // Assign to presenter field

        handler = new Handler(Looper.getMainLooper());
        checkNetworkPeriodically();
    }

    @Override
    public void showProducts(List<Product> products) {
        Log.i(TAG, "showProducts: ");
        for(Product product: products){
            Log.i(TAG, "ID: " + product.id);
        }
        allAdapter = new AllAdapter(AllActivity.this, products, this);
        allRecyclerView.setAdapter(allAdapter);
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }

    @Override
    public void onAddToCartClicked(Product product) {
        presenter.addToCart(product);
    }
    private void checkNetworkPeriodically() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkUtils.isNetworkAvailable(AllActivity.this)) {
                    if (snackbar != null && snackbar.isShown()) {
                        snackbar.dismiss();
                    }
                    if (!productsLoaded) {
                        presenter.loadProducts();
                        productsLoaded = true;
                    }
                } else {
                    if (snackbar == null || !snackbar.isShown()) {
                        showNetworkSettingsSnackbar();
                    }
                }
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        }, CHECK_INTERVAL);
    }
    private void showNetworkSettingsSnackbar() {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
    }

}