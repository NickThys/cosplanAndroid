package com.example.cosplan.data.Coplay.RefImg;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReferenceImgDao {
    @Insert
    void insert(ReferenceImg referenceImg);

    @Query("SELECT * FROM CosplayRefImg_table WHERE CosplayId=:CosplayId")
    LiveData<List<ReferenceImg>> getReferenceImg(final int CosplayId);
    @Delete
    void delete(ReferenceImg referenceImg);
}
