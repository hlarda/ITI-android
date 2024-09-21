package com.example.mvc_room_retrofit.allProducts.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvc_room_retrofit.R;
import com.example.mvc_room_retrofit.model.Product;

import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ProductViewHolder> {
    private final Context context;
    private final List<Product> products;
    private final AllListener allListener;

    public AllAdapter(Context context, List<Product> products, AllListener allListener) {
        this.context = context;
        this.products = products;
        this.allListener = allListener;
    }
    @NonNull
    @Override
    public AllAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_frame, parent, false);
        return new AllAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAdapter.ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleView.setText(product.title);
        holder.descriptionView.setText(product.description);
        holder.priceView.setText(String.valueOf(product.price));
        holder.brandView.setText(product.brand);
        holder.ratingBar.setRating(Float.parseFloat(String.valueOf(product.rating)));
        Glide.with(context).load(product.thumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.rmFromCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (allListener != null)
                    allListener.onAddToCartClicked(product);
                Toast.makeText(context, product.title + " added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        TextView descriptionView;
        public TextView priceView;
        public TextView brandView;
        RatingBar ratingBar;
        public ImageView imageView;
        public Button rmFromCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            priceView = itemView.findViewById(R.id.priceView);
            brandView = itemView.findViewById(R.id.brandView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);
            rmFromCart = itemView.findViewById(R.id.rmFromCart);
        }
    }

}
