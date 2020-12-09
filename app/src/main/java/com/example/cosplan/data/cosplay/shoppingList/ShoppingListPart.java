package com.example.cosplan.data.cosplay.shoppingList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "CosplayShoppingList_table",foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Cosplay.class,parentColumns = "Id",childColumns = "CosplayId"))
public class ShoppingListPart {
    @ColumnInfo(name = "CosplayId",index = true)
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

    @Ignore
    public ShoppingListPart() {}
    public ShoppingListPart(int mCosplayId, int mCosplayShoppingLIstPartId, @NotNull String mCosplayShoppingListPartName , @NotNull String  mCosplayShoppingListPartShop, boolean mCosplayShoppingListPartChecked) {
        this.mCosplayId = mCosplayId;
        this.mCosplayShoppingLIstPartId = mCosplayShoppingLIstPartId;
        this.mCosplayShoppingListPartShop = mCosplayShoppingListPartShop;
        this.mCosplayShoppingListPartName = mCosplayShoppingListPartName;
        this.mCosplayShoppingListPartChecked = mCosplayShoppingListPartChecked;
    }
}
