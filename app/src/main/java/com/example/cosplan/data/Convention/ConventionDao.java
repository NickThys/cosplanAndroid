package com.example.cosplan.data.Convention;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConventionDao {
    @Insert
    void insert(Convention convention);
    @Query("SELECT * FROM convention_table WHERE Country='Belgium'")
    LiveData<List<Convention>> getAllConventionsBelgium();

    @Query("SELECT * FROM convention_table WHERE Country='Netherland'")
    LiveData<List<Convention>> getAllConventionsNetherland();
    @Query("SELECT *FROM convention_table LIMIT 1")
    Convention[] getAnyConvention();
}
