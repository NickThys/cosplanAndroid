package com.example.cosplan.data.cosplay.part;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "CosplayPart_table", foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE ,entity = Cosplay.class,
        parentColumns = "Id",
        childColumns = "CosplayId"))
public class Part {
    @ColumnInfo(name = "CosplayId",index = true)
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
    @ColumnInfo(name = "CosplayPartNote")
    public String mCosplayPartNote;

    @Ignore
    public Part(){}
    //Basic input for a new part
    public Part(int cosplayId, int partId, @NonNull String partName, @NotNull String partMakeBuy, String partLink, double partCost, String partStatus, String partEndDate, Bitmap partImage){
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

    public Part(int mCosplayId, int mCosplayPartId, @NonNull String mCosplayPartName, @NonNull String mCosplayPartBuyMake, String mCosplayPartLink, double mCosplayPartCost, String mCosplayPartStatus, String mCosplayPartEndDate, Bitmap mCosplayPartImg, String mCosplayPartNote) {
        this.mCosplayId = mCosplayId;
        this.mCosplayPartId = mCosplayPartId;
        this.mCosplayPartName = mCosplayPartName;
        this.mCosplayPartBuyMake = mCosplayPartBuyMake;
        this.mCosplayPartLink = mCosplayPartLink;
        this.mCosplayPartCost = mCosplayPartCost;
        this.mCosplayPartStatus = mCosplayPartStatus;
        this.mCosplayPartEndDate = mCosplayPartEndDate;
        this.mCosplayPartImg = mCosplayPartImg;
        this.mCosplayPartNote = mCosplayPartNote;
    }
}
