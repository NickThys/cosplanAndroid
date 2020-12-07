package com.example.cosplan.data.cosplay.Webshop;

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
    void delete(Webshop webshop);
    @Update
    void update(Webshop webshop);
    @Query("SELECT * FROM cosplaywebshop_table WHERE CosplayId=:CosplayId")
    LiveData<List<Webshop>> getAllWebshops(final int CosplayId);
}
