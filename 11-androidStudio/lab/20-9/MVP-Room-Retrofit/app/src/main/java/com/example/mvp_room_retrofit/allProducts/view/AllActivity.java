package com.example.mvp_room_retrofit.allProducts.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp_room_retrofit.R;
import com.example.mvp_room_retrofit.allProducts.presenter.AllPresenter;
import com.example.mvp_room_retrofit.model.Product;
import com.example.mvp_room_retrofit.db.ProductRepository;

import java.util.List;

public class AllActivity extends AppCompatActivity implements AllView, AllListener {
    RecyclerView allRecyclerView;
    RecyclerView.LayoutManager allLayoutManager;
    AllAdapter   allAdapter;
    AllPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.all_products);

        allRecyclerView = findViewById(R.id.allRecyclerView);
        allLayoutManager = new LinearLayoutManager(AllActivity.this, LinearLayoutManager.VERTICAL, false);
        allRecyclerView.setLayoutManager(allLayoutManager);

        presenter = new AllPresenter(this, new ProductRepository(this)); // Assign to presenter field
        presenter.loadProducts();    }

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
}