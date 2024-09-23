package com.example.mvp_room_retrofit.cartProducts.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mvp_room_retrofit.cartProducts.view.CartView;
import com.example.mvp_room_retrofit.model.Product;
import com.example.mvp_room_retrofit.db.ProductRepository;

import java.util.List;

public class CartPresenter {
    private final CartView view;
    private final ProductRepository productRepository;

    public CartPresenter(CartView view, ProductRepository productRepository) {
        this.view = view;
        this.productRepository = productRepository;
    }

    public void loadCartProducts() {
        LiveData<List<Product>> cartList = productRepository.getProducts();
        cartList.observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                view.showCartProducts(products);
            }
        });
    }

    public void removeFromCart(Product product) {
        productRepository.deleteProduct(product);
    }
}
