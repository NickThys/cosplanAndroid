package com.example.cosplan.data.Coplay.CheckList;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cosplan.data.Coplay.Webshop.Webshop;

import java.util.List;

@Dao
public interface CheckListPartDao {
    @Insert
    void insert(ChecklistPart checklistPart);
    @Delete
    void delete(ChecklistPart checklistPart);
    @Update
    void Update(ChecklistPart checklistPart);
    @Query("SELECT*FROM cosplaychecklist_table WHERE CosplayId=:CosplayId")
    LiveData<List<Webshop>> getAllCheckListParts(final int CosplayId);
}
