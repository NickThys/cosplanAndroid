package com.example.cosplan.data.Coplay.ShoppingList;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListPartDao {
    @Insert
    void insert(ShoppingListPart shoppingListPart);
    @Delete
    void delete(ShoppingListPart shoppingListPart);
    @Update
    void update(ShoppingListPart shoppingListPart);
    @Query("SELECT DISTINCT CosplayShoppingListPartShop from CosplayShoppingList_table where CosplayId=:CosplayId")
    LiveData<List<ShoppingListPart>> getAllNamesFromStores(final int CosplayId);
    @Query("SELECT * FROM CosplayShoppingList_table WHERE CosplayId=:CosplayId AND CosplayShoppingListPartShop=:PartShop")
    LiveData<List<ShoppingListPart>> getAllShoppingListPartsFromShop(final int CosplayId,final String PartShop);
}
