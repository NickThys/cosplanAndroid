package com.example.cosplan.data.Coplay.WIPImg;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WIPImgDao {
    @Insert
    void insert(WIPImg wipImg);

    @Query("SELECT * FROM CosplayWIPImg_table WHERE CosplayId=:CosplayId")
    LiveData<List<WIPImg>> getWIPImg(final int CosplayId);
}
