package com.example.cosplan.data.cosplay.events;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);
    @Delete
    void delete(Event event);
    @Update
    void update(Event event);

    @Query("SELECT * FROM CosplayEvent_table WHERE CosplayId=:CosplayId AND CosplayEventType=:CosplayEventType")
    LiveData<List<Event>> getEventsByType(final int CosplayId,final String CosplayEventType);
}
