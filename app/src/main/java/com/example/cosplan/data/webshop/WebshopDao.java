package com.example.cosplan.data.webshop;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface WebshopDao {
    @Insert
    void insert(Webshop webshop);

    @Delete
    void deleteWebshop(Webshop webshop);
    @Update
    void updateWebshop(Webshop webshop);
    @Query("SELECT * FROM webshop_table ORDER BY Name ASC")
    LiveData<List<Webshop>> getAllWebshops();
    @Query("SELECT * FROM webshop_table LIMIT 1")
    Webshop[]getAnyWebshop();
}
