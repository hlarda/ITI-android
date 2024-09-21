package com.example.mvc_room_retrofit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvc_room_retrofit.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM products_table")
    List<Product> getProducts();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Product movie);

    @Delete
    void deleteProduct(Product product);
}
