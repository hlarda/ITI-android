package com.example.cakerecycleview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    Cake[] cakes = {
            new Cake("Cake 1", "This is cake 1", R.drawable.a),
            new Cake("Cake 2", "This is cake 2", R.drawable.d),
            new Cake("Cake 3", "This is cake 3", R.drawable.f),
            new Cake("Cake 4", "This is cake 4", R.drawable.h),
            new Cake("Cake 5", "This is cake 5", R.drawable.s),
            new Cake("Cake 6", "This is cake 6", R.drawable.k),
            new Cake("Cake 7", "This is cake 7", R.drawable.j),
            new Cake("Cake 8", "This is cake 7", R.drawable.g)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CakeAdapter adapter = new CakeAdapter(getApplicationContext(), cakes);
        recyclerView.setAdapter(adapter);
    }
}