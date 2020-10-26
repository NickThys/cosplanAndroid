package com.example.cosplan.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WebshopDao {
    @Insert
    void insert(Webshop webshop);

    @Delete
    void deleteWebshop(Webshop webshop);

    @Query("SELECT * FROM webshop_table ORDER BY name ASC")
    LiveData<List<Webshop>> getAllWebshops();
}
