package com.example.mvp_room_retrofit.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvp_room_retrofit.model.Product;
import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM products_table")
    LiveData<List<Product>> getProducts();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Product movie);

    @Delete
    void deleteProduct(Product product);
}
