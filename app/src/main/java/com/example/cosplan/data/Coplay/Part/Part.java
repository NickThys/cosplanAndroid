package com.example.cosplan.data.Coplay.Part;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.Coplay.Cosplay;

@Entity(tableName = "CosplayPart_table", foreignKeys = @ForeignKey(entity = Cosplay.class,
        parentColumns = "Id",
        childColumns = "CosplayId"))
public class Part {
    @ColumnInfo(name = "CosplayId")
    public int mCosplayId;

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
    @ColumnInfo(name = "CosplayPartStatus")
    public String mCosplayPartStatus;
    @ColumnInfo(name = "CosplayPartEndDate")
    public String mCosplayPartEndDate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "CosplayPartImage")
    public Bitmap mCosplayPartImg;

    public Part(){}
    //Basic input for a new part
    public Part(@NonNull int cosplayId, @NonNull int partId, @NonNull String partName, String partMakeBuy, String partLink, double partCost, String partStatus, String partEndDate, Bitmap partImage){
        mCosplayId=cosplayId;
        mCosplayPartId=partId;
        mCosplayPartName=partName;
        mCosplayPartBuyMake=partMakeBuy;
        mCosplayPartLink=partLink;
        mCosplayPartCost=partCost;
        mCosplayPartStatus=partStatus;
        mCosplayPartEndDate=partEndDate;
        mCosplayPartImg=partImage;
    }

}
