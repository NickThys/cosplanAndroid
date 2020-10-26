package com.example.cosplan.data.Convention;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConventionDao {

    @Query("SELECT * FROM convention_table WHERE Country='Belgium'")
    LiveData<List<Convention>> getAllConventionsBelgium();

    @Query("SELECT * FROM convention_table WHERE Country='Netherland'")
    LiveData<List<Convention>> getAllConventionsNetherland();
}
