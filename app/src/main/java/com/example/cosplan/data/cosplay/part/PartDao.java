package com.example.cosplan.data.cosplay.part;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PartDao {
    @Insert
    void insert(Part part);
    @Query("SELECT * FROM CosplayPart_table WHERE CosplayPartBuyMake='Make' AND CosplayId=:CosplayId")
    LiveData<List<Part>> getPartsToMake(final int CosplayId);
    @Query("SELECT * FROM CosplayPart_table WHERE CosplayPartBuyMake='Buy' AND CosplayId=:CosplayId")
    LiveData<List<Part>> getPartsToBuy(final int CosplayId);
    @Delete
    void delete(Part part);

    @Update
    void update(Part part);

    @Query("SELECT * FROM CosplayPart_table WHERE CosplayPartId=(SELECT MAX(CosplayPartId)FROM COSPLAYPART_TABLE)AND CosplayId=:CosplayId")
    Part getLastCreatedPart(final int CosplayId);
}
