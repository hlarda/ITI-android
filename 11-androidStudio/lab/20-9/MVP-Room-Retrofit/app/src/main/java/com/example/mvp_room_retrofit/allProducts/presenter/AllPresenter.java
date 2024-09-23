package com.example.mvp_room_retrofit.allProducts.presenter;

import com.example.mvp_room_retrofit.allProducts.view.AllView;
import com.example.mvp_room_retrofit.model.Product;
import com.example.mvp_room_retrofit.network.NetworkCallback;
import com.example.mvp_room_retrofit.network.ProductClient;
import com.example.mvp_room_retrofit.db.ProductRepository;

import java.util.List;

public class AllPresenter implements NetworkCallback {
    private final AllView view;
    private final ProductRepository productRepository;

    public AllPresenter(AllView view, ProductRepository productRepository) {
        this.view = view;
        this.productRepository = productRepository;
    }

    public void loadProducts() {
        ProductClient productClient = ProductClient.getInstance();
        productClient.getProducts(this);
    }

    @Override
    public void onSuccess(List<Product> response) {
        view.showProducts(response);
    }

    @Override
    public void onFailure(String error) {
        view.showError(error);
    }

    public void addToCart(Product product) {
        productRepository.insert(product);
    }
}