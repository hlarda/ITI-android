package com.example.cakerecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {
    private final Context context;
    private final Cake[] cakes;

    public CakeAdapter(Context context, Cake[] cakes) {
        this.context = context;
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View           view     = inflater.inflate(R.layout.cake_frame, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        holder.imageView.setImageResource(cakes[position].getImage());
        holder.titleView.setText(cakes[position].getName());
        holder.descriptionView.setText(cakes[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return cakes.length;
    }

    public static class CakeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView, descriptionView;
        public View view;

        public CakeViewHolder(@NonNull View itemView) {
            super(Objects.requireNonNull(itemView));
            this.view = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
        }
    }
}
