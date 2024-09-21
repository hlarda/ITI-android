package com.example.mvc_room_retrofit.cartProducts.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvc_room_retrofit.R;
import com.example.mvc_room_retrofit.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<com.example.mvc_room_retrofit.allProducts.view.AllAdapter.ProductViewHolder> {
    private final Context context;
    private final List<Product> products;
    private final CartListener cartListener;

    public CartAdapter(Context context, List<Product> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
    }
    @NonNull
    @Override
    public com.example.mvc_room_retrofit.allProducts.view.AllAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_product, parent, false);
        return new com.example.mvc_room_retrofit.allProducts.view.AllAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.mvc_room_retrofit.allProducts.view.AllAdapter.ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleView.setText(product.title);
        holder.priceView.setText(String.valueOf(product.price));
        holder.brandView.setText(product.brand);
        Glide.with(context).load(product.thumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.rmFromCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (cartListener != null)
                    cartListener.onRemoveFromCartClicked(product);
                Toast.makeText(context, product.title + " removed from cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, priceView, brandView;
        ImageView imageView;
        Button removeFromCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            priceView = itemView.findViewById(R.id.priceView);
            brandView = itemView.findViewById(R.id.brandView);
            imageView = itemView.findViewById(R.id.imageView);
            removeFromCart = itemView.findViewById(R.id.rmFromCart);
        }
    }

}
