package com.example.cosplan.data.Coplay;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CosplayDao {
    @Insert
    void insert(Cosplay cosplay);

    @Query("SELECT *FROM cosplay_table")
    LiveData<List<Cosplay>> getAllCosplays();

    @Query("SELECT * FROM cosplay_table LIMIT 1")
    Cosplay[] getAnyCosplay();

}
