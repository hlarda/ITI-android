package com.example.mvp_room_retrofit.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mvp_room_retrofit.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private final ProductDAO productDAO;
    private final ExecutorService executorService;

    public ProductRepository(Context context) {
        ProductDatabase db = ProductDatabase.getInstance(context);
        productDAO = db.getProductDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Product>> getProducts() {
        return productDAO.getProducts();
    }

    public void insert(Product product) {
        executorService.execute(() -> productDAO.insert(product));
    }

    public void deleteProduct(Product product) {
        executorService.execute(() -> productDAO.deleteProduct(product));
    }
}
