package com.example.cosplan.data.cosplay.refImg;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cosplan.data.cosplay.Cosplay;

@Entity(tableName = "CosplayRefImg_table",
        foreignKeys = @ForeignKey(onDelete = ForeignKey.CASCADE, entity = Cosplay.class,
                parentColumns = "Id",
                childColumns = "CosplayId"))
public class ReferenceImg {
    @ColumnInfo(name = "CosplayId", index = true)
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayRefImgId")
    public int mCosplayRefImgId;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "CosplayRefImgImage")
    public Bitmap mCosplayRefImgImage;


    public ReferenceImg() {
    }

    public ReferenceImg(int cosplayId, int RefImgId, @NonNull Bitmap RefImgImage) {
        mCosplayId = cosplayId;
        mCosplayRefImgId = RefImgId;
        mCosplayRefImgImage = RefImgImage;
    }

}
