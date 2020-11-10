package com.example.cosplan.data.Cosplay_Part;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.Coplay.Cosplay;

@Entity(tableName = "CosplayPart_table", foreignKeys = @ForeignKey(entity = Cosplay.class,
        parentColumns = "CosplayId",
        childColumns = "CosplayId"))
public class Part {
    @ColumnInfo(name = "CosplayId")
    private int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayPartId")
    public int mCosplayPartId;
    @NonNull
    @ColumnInfo(name = "CosplayPartName")
    public String mCosplayPartName;
    @NonNull
    @ColumnInfo(name = "CosplayPartBuyMake")
    public String mCosplayPartBuyMake;
    @ColumnInfo(name = "CosplayPartLink")
    public String mCosplayPartLink;
    @ColumnInfo(name = "CosplayPartCost")
    public double mCosplayPartCost;
    @ColumnInfo(name = "CosplayPartEndDate")
    public String mCosplayPartEndDate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "CosplayPartImage")
    public Bitmap mCosplayPartImg;
    // TODO: 10/11/2020 Define the constructors 

}
