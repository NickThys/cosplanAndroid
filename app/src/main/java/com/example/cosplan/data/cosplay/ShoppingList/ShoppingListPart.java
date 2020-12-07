package com.example.cosplan.data.cosplay.ShoppingList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

@Entity(tableName = "CosplayShoppingList_table",foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Cosplay.class,parentColumns = "Id",childColumns = "CosplayId"))
public class ShoppingListPart {
    @ColumnInfo(name = "CosplayId")
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayShoppingListPartId")
    public int mCosplayShoppingLIstPartId;

    @ColumnInfo(name = "CosplayShoppingListPartShop")
    public String mCosplayShoppingListPartShop;

    @ColumnInfo(name = "CosplayShoppingListPartName")
    public String mCosplayShoppingListPartName;

    @ColumnInfo(name = "CosplayShoppingListPartChecked")
    public boolean mCosplayShoppingListPartChecked;


    public ShoppingListPart() {}
    public ShoppingListPart(int mCosplayId, int mCosplayShoppingLIstPartId, String mCosplayShoppingListPartName , String  mCosplayShoppingListPartShop, boolean mCosplayShoppingListPartChecked) {
        this.mCosplayId = mCosplayId;
        this.mCosplayShoppingLIstPartId = mCosplayShoppingLIstPartId;
        this.mCosplayShoppingListPartShop = mCosplayShoppingListPartShop;
        this.mCosplayShoppingListPartName = mCosplayShoppingListPartName;
        this.mCosplayShoppingListPartChecked = mCosplayShoppingListPartChecked;
    }
}
