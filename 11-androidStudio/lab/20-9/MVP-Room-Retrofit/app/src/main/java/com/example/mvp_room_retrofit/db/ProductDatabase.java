package com.example.mvp_room_retrofit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvp_room_retrofit.model.Product;

@Database(entities = {Product.class},version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance = null;
    public abstract ProductDAO getProductDAO();
    public static synchronized ProductDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "productsDb")
                    .build();
        }
        return instance;
    }
}
