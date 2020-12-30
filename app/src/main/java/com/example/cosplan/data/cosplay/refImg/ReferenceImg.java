package com.example.cosplan.data.cosplay.refImg;


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
    @ColumnInfo(name = "CosplayId",index = true)
    public int mCosplayId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CosplayRefImgId")
    public int mCosplayRefImgId;


    @ColumnInfo(name = "CosplayRefImgImage")
    public String mCosplayRefImgImage;


    public ReferenceImg() {
    }

    public ReferenceImg(int cosplayId, int RefImgId,String RefImgImage) {
        mCosplayId = cosplayId;
        mCosplayRefImgId = RefImgId;
        mCosplayRefImgImage = RefImgImage;
    }

}
